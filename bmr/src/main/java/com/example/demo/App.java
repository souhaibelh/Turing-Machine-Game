package com.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Properties;

public class App extends Application {
    private final String DEFAULT_HEIGHT_UNIT = "cm";
    private final String DEFAULT_WEIGHT_UNIT = "kg";

    private final Text helpTexta = new Text("");
    private final Text helpTextw = new Text("");
    private final Text helpTexth = new Text("");

    private double current_height;
    private double current_weight;
    private double current_age;
    private boolean isWoman;
    private double current_lifestylefr;
    private final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void start(Stage stage) throws IOException {
        File settings = new File("./settings");
        helpTexta.setManaged(false);
        if (settings.mkdir()) {
            System.out.println("Folder created");
        }
        setDefaultSettings();
        Properties settings_prop = new Properties();
        FileInputStream reader =  new FileInputStream("./settings/bmrsettings.properties");
        settings_prop.load(reader);
        reader.close();

        StringBuilder regex_weight = new StringBuilder();
        for (int i=0; i<WeightUnits.values().length; i++) {
            String separator = "";
            if (i != WeightUnits.values().length - 1) {
                separator += "|";
            }
            regex_weight.append(WeightUnits.values()[i].getShortName()).append(separator);
        }

        StringBuilder regex_height = new StringBuilder();
        for (int i=0; i<HeightUnits.values().length; i++) {
            String separator = "";
            if (i != HeightUnits.values().length - 1) {
                separator += "|";
            }
            regex_height.append(HeightUnits.values()[i].getShortName()).append(separator);
        }

        if (!settings_prop.getProperty("WeightUnit").matches(regex_weight.toString())) {
            settings_prop.setProperty("WeightUnit", DEFAULT_WEIGHT_UNIT);
            updateSettingsFile(settings_prop, "Weight unit rewritten");
        } else if (!settings_prop.getProperty("HeightUnit").matches(regex_height.toString())) {
            settings_prop.setProperty("HeightUnit", DEFAULT_HEIGHT_UNIT);
            updateSettingsFile(settings_prop, "Height unit rewritten");
        }

        StringProperty WeightUnit = new SimpleStringProperty(settings_prop.getProperty("WeightUnit"));
        StringProperty HeightUnit = new SimpleStringProperty(settings_prop.getProperty("HeightUnit"));

        Clipboard win_clipboard = Clipboard.getSystemClipboard();
        ClipboardContent copy_content = new ClipboardContent();
        GridPane left_grid = new GridPane();
        GridPane right_grid = new GridPane();
        HBox hBox = new HBox();
        VBox vBox = new VBox();

        vBox.setAlignment(Pos.CENTER);
        left_grid.setVgap(10);
        right_grid.setVgap(10);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10));
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(0,0,10,0));

        MenuBar menu = new MenuBar();
        Menu file = new Menu("File");
        MenuItem settings_menu = new MenuItem("Settings");
        MenuItem exit = new MenuItem("Exit");
        file.getItems().add(settings_menu);
        file.getItems().add(exit);
        menu.getMenus().add(file);

        exit.setOnAction((e) -> Platform.exit());

        Label data = new Label("Data");
        data.setUnderline(true);
        left_grid.add(data,2,1);

        Label height_label = new Label("Height (" + HeightUnit.get() + ")");
        height_label.getStyleClass().add("textData");
        height_label.getStylesheets().add(getClass().getResource("general.css").toExternalForm());
        TextField height_input = new TextField();
        height_input.getProperties().put("name", "h");
        height_input.getProperties().put("textHelp", "Must be greater than 0");
        height_input.setPromptText("Height in " + HeightUnit.get());
        left_grid.add(height_label, 2, 2);
        left_grid.add(height_input, 3, 2);

        Label weight_label = new Label("Weight (" + WeightUnit.get() + ")");
        TextField weight_input = new TextField();
        weight_input.getProperties().put("name","w");
        weight_input.getProperties().put("textHelp", "Must be greater than 0");
        weight_input.setPromptText("Weight in " + WeightUnit.get());
        left_grid.add(weight_label,2,3);
        left_grid.add(weight_input, 3, 3);

        WeightUnit.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                weight_label.setText("Weight (" + newValue + ")");
                weight_input.setPromptText("Weight in " + newValue);
            }
        });

        HeightUnit.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                height_label.setText("Height (" + newValue + ")");
                height_input.setPromptText("Height in " + newValue);
            }
        });

        Label age_label = new Label("Age (Year)");
        TextField age_input = new TextField();
        age_input.getProperties().put("name", "a");
        age_input.getProperties().put("textHelp", "Must be greater than 0");
        age_input.setPromptText("Age in years");
        left_grid.add(age_label,2,4);
        left_grid.add(age_input, 3, 4);
        age_input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty() && Integer.parseInt(newValue) <= 0) {
                    helpTexta.setVisible(true);
                    helpTexta.setText("Age must be > 0");
                    helpTexta.setLayoutY(age_input.getLayoutY());
                    helpTexta.setLayoutX(age_input.getLayoutX());
                } else {
                    helpTexta.setVisible(false);
                }
            }
        });

        weight_input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.contains(",")) {
                    newValue = newValue.replace(",",".");
                }
                if (!newValue.isEmpty() && Double.parseDouble(newValue) <= 0) {
                    helpTextw.setVisible(true);
                    helpTextw.setText("Weight must be > 0");
                    helpTextw.setLayoutY(weight_input.getLayoutY());
                    helpTextw.setLayoutX(weight_input.getLayoutX());
                } else {
                    helpTextw.setVisible(false);
                }
            }
        });

        height_input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.contains(",")) {
                    newValue = newValue.replace(",",".");
                }
                if (!newValue.isEmpty() && Double.parseDouble(newValue) <= 0) {
                    helpTexth.setVisible(true);
                    helpTexth.setText("Height must be > 0");
                    helpTexth.setLayoutY(height_input.getLayoutY());
                    helpTexth.setLayoutX(height_input.getLayoutX());
                } else {
                    helpTexth.setVisible(false);
                }
            }
        });

        Label gender_label = new Label("Sex");
        ToggleGroup gender_input_container = new ToggleGroup();
        RadioButton female_radiobutton = new RadioButton("Woman");
        RadioButton male_radiobutton = new RadioButton("Man");
        female_radiobutton.setSelected(true);
        gender_input_container.getToggles().addAll(male_radiobutton,female_radiobutton);
        left_grid.add(gender_label,2,5);
        HBox gender_container = new HBox(male_radiobutton,female_radiobutton);
        gender_container.setSpacing(5);
        left_grid.add(gender_container, 3, 5);

        Label lifestyle_label = new Label("Lifestyle    ");
        Lifestyle[] lifestyles = Lifestyle.values();
        ComboBox<Lifestyle> lifestyle_input = new ComboBox<>();
        lifestyle_input.getItems().setAll(lifestyles);
        lifestyle_input.getSelectionModel().selectFirst();
        left_grid.add(lifestyle_label,2,6);
        left_grid.add(lifestyle_input, 3, 6);
        Label results = new Label("Results");
        results.setUnderline(true);
        right_grid.add(results,2,1);

        Image clipboard_icon = new Image("https://i.postimg.cc/25BXhtxn/clipboard.png");
        ImageView clipboard = new ImageView(clipboard_icon);
        ImageView clipboard_two = new ImageView(clipboard_icon);
        clipboard.setFitHeight(15);
        clipboard.setFitWidth(15);
        clipboard_two.setFitHeight(15);
        clipboard_two.setFitWidth(15);

        Label bmr_label = new Label("BMR");
        TextField bmr_input = new TextField();
        bmr_input.setPromptText("Your BMR");
        bmr_input.setEditable(false);
        Button bmr_cpbutton = new Button();
        bmr_cpbutton.setGraphic(clipboard_two);
        HBox bmr_buttons = new HBox(bmr_input,bmr_cpbutton);
        bmr_buttons.setSpacing(10);
        right_grid.add(bmr_label,2,2);
        right_grid.add(bmr_buttons,3,2);
        bmr_cpbutton.setOnAction((e) -> {
            copy_content.putString(bmr_input.getText());
            win_clipboard.setContent(copy_content);
        });

        Label calories_label = new Label("Calories/day");
        TextField calories_input = new TextField();
        calories_input.setPromptText("Calorie expenditure");
        calories_input.setEditable(false);
        Button clipboard_calories = new Button();
        clipboard_calories.setGraphic(clipboard);
        HBox calories_buttons = new HBox(calories_input,clipboard_calories);
        calories_buttons.setSpacing(10);
        right_grid.add(calories_label,2,3);
        right_grid.add(calories_buttons,3,3);
        clipboard_calories.setOnAction((e) -> {
            copy_content.putString(calories_input.getText());
            win_clipboard.setContent(copy_content);
        });

        BooleanBinding empty_parameters = age_input.textProperty().isEmpty()
                                          .or(weight_input.textProperty().isEmpty())
                                          .or(height_input.textProperty().isEmpty());

        Button bmr_btn = new Button("Calculate");
        bmr_btn.setOnAction((e) -> {
            String height = height_input.getText();
            String weight = weight_input.getText();
            String age = age_input.getText();
            if (height.contains(",")) {
                height = height.replace(",",".");
            }
            if (weight.contains(",")) {
                weight = weight.replace(",",".");
            }

            if (empty_parameters.get()) {
                bmr_input.setText("Failed!");
                calories_input.setText("Failed!");
                calories_input.setStyle("-fx-text-fill: red");
                bmr_input.setStyle("-fx-text-fill: red");
            } else if (Double.parseDouble(height) == 0.0) {
                createAlert("Wrong height value", "Height must be strictly bigger than 0",stage);
            } else if (Double.parseDouble(weight) == 0.0) {
                createAlert("Wrong weight value", "Weight must be strictly bigger than 0",stage);
            } else if (Double.parseDouble(age) == 0.0) {
                createAlert("Wrong age value", "Age must be strictly bigger than 0",stage);
            } else {
                current_age = Double.parseDouble(age);
                current_weight = Double.parseDouble(weight);
                current_height = Double.parseDouble(height);
                isWoman = female_radiobutton.isSelected();
                current_lifestylefr = lifestyle_input.getValue().getFactor();

                double current_weightfr = WeightUnits.valueOf(WeightUnit.get().toUpperCase()).getFactor();
                double current_heightfr = HeightUnits.valueOf(HeightUnit.get().toUpperCase()).getFactor();
                double bmr = isWoman ? computeBmrWoman(current_weight,current_height,current_weightfr,current_heightfr,current_age) :
                        computeBmrMan(current_weight,current_height,current_weightfr,current_heightfr,current_age);

                bmr = Double.parseDouble(df.format(bmr));
                double calories = calculateCalories(bmr, current_lifestylefr);

                bmr_input.setText(String.valueOf(bmr));
                calories_input.setText(df.format(calories));
                calories_input.setStyle("-fx-text-fill: black");
                bmr_input.setStyle("-fx-text-fill: black");
            }
        });

        settings_menu.setOnAction((e) -> {
            Stage change_units = new Stage();
            VBox center = new VBox();
            center.getStyleClass().add("hbox");
            BorderPane main_pane = new BorderPane();
            main_pane.setCenter(center);
            GridPane layout = new GridPane();
            HBox buttons = new HBox();
            Button save = new Button("Save");
            save.getStyleClass().add("bluebutton");
            Button cancel = new Button("Cancel");
            cancel.getStyleClass().add("nocolorbutton");
            WeightUnits current_weight_unit = WeightUnits.valueOf(WeightUnit.get().toUpperCase());
            HeightUnits current_height_unit = HeightUnits.valueOf(HeightUnit.get().toUpperCase());

            cancel.setOnAction((f) -> {
                change_units.close();
            });

            Label weight = new Label("Weight: ");
            Label height = new Label("Height: ");

            ComboBox<WeightUnits> weight_units = new ComboBox<>();
            weight_units.getItems().setAll(WeightUnits.values());
            weight_units.setValue(current_weight_unit);

            ComboBox<HeightUnits> height_units = new ComboBox<>();
            height_units.getItems().setAll(HeightUnits.values());
            height_units.setValue(current_height_unit);
            height_units.getStyleClass().add("test");

            save.setOnAction((f) -> {
                boolean changed = false;
                if (!WeightUnit.get().equals(weight_units.getValue().getShortName())) {
                    settings_prop.setProperty("WeightUnit", weight_units.getValue().getShortName());
                    WeightUnit.set(weight_units.getValue().getShortName());
                    changed = true;
                }
                if (!HeightUnit.get().equals(height_units.getValue().getShortName())) {
                    settings_prop.setProperty("HeightUnit", height_units.getValue().getShortName());
                    HeightUnit.set(height_units.getValue().getShortName());
                    changed = true;
                }
                if (!settings_prop.isEmpty()) {
                    updateSettingsFile(settings_prop, "Updated Units");
                }
                if (changed) {
                    double weight_factor = WeightUnits.valueOf(WeightUnit.get().toUpperCase()).getFactor();
                    double height_factor = HeightUnits.valueOf(HeightUnit.get().toUpperCase()).getFactor();
                    double bmr = isWoman ? computeBmrWoman(current_weight,current_height,weight_factor,height_factor,current_age) :
                            computeBmrMan(current_weight,current_height,weight_factor,height_factor,current_age);
                    double calories = bmr * current_lifestylefr;
                    bmr_input.setText(df.format(bmr));
                    calories_input.setText(df.format(calories));
                }
            });

            layout.add(weight,1,1);
            layout.add(height,1,2);
            layout.add(weight_units,2,1);
            layout.add(height_units,2,2);

            buttons.getChildren().addAll(save,cancel);

            buttons.setAlignment(Pos.BOTTOM_RIGHT);
            center.getChildren().addAll(layout,buttons);
            buttons.setSpacing(10);
            center.setSpacing(10);
            layout.setVgap(10);
            center.setPadding(new Insets(10));

            Scene scene = new Scene(main_pane);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("general.css")).toExternalForm());

            change_units.initModality(Modality.WINDOW_MODAL);
            change_units.setScene(scene);
            change_units.initStyle(StageStyle.UTILITY);
            change_units.setResizable(false);
            change_units.setTitle("Change units");
            change_units.initOwner(stage);
            change_units.show();
            change_units.setX(stage.getX() + (stage.getWidth() - change_units.getWidth())/2);
            change_units.setY(stage.getY() + (stage.getHeight() - change_units.getHeight())/2);
        });

        helpTexta.setManaged(false);
        helpTextw.setManaged(false);
        helpTexth.setManaged(false);
        helpTexta.setTextOrigin(VPos.BOTTOM);
        helpTexta.setFill(Color.RED);
        helpTextw.setFill(Color.RED);
        helpTexth.setFill(Color.RED);
        left_grid.getChildren().addAll(helpTexta,helpTextw,helpTexth);

        Button clear = new Button("Clear");
        clear.setOnAction((e) -> {
            height_input.clear();
            weight_input.clear();
            age_input.clear();
            bmr_input.clear();
            calories_input.clear();
            lifestyle_input.setValue(lifestyles[0]);
        });

        hBox.getChildren().addAll(left_grid,right_grid);
        vBox.getChildren().addAll(menu,hBox,bmr_btn,clear);

        Scene main_scene = new Scene(vBox);
        menu.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(MouseEvent event1) {
                menu.setOnMouseDragged(new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() - event1.getSceneX());
                        stage.setY(event.getScreenY() - event1.getSceneY());
                    }
                });
            }
        });

        age_input.addEventFilter(KeyEvent.KEY_TYPED, (e) -> {
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        });

        weight_input.addEventFilter(KeyEvent.KEY_TYPED, (e) -> {
            if (invalidInput(e.getCharacter(), weight_input.getText())) {
                e.consume();
            }
        });

        height_input.addEventFilter(KeyEvent.KEY_TYPED, (e) -> {
            if (invalidInput(e.getCharacter(), height_input.getText())) {
                e.consume();
            }
        });

        hBox.getStyleClass().add("hbox");
        bmr_btn.getStyleClass().add("greenbutton");
        clear.getStyleClass().add("redbutton");
        main_scene.getRoot().getStylesheets().add(getClass().getResource("general.css").toString());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("BMR Calculator");
        stage.setScene(main_scene);
        stage.show();
        stage.setResizable(false);
        clear.setPrefWidth(vBox.getWidth() - 20);
        bmr_btn.setPrefWidth(vBox.getWidth() - 20);
        vBox.setStyle("-fx-border-color: grey");
        main_scene.getRoot().getStyleClass().add("root2");
    }

    private boolean fileAvailable() throws IOException {
        File settings_folder = new File("./settings");
        File properties_file = new File("./settings","bmrsettings.properties");
        if (settings_folder.mkdir() || properties_file.createNewFile()) {
            return false;
        }
        return true;
    }


    private double computeBmrWoman(double weight, double height, double weightfactor, double heightfactor, double age) {
        return 9.6 * weight/weightfactor + 1.8 * height/heightfactor - 4.7 * age + 655;
    }

    private double computeBmrMan(double weight, double height, double weightfactor, double heightfactor, double age) {
        return 13.7 * weight/weightfactor + 5 * height/heightfactor - 6.8 * age + 66;
    }

    private double calculateCalories(double bmr, double factor) {
        return bmr * factor;
    }

    private void updateSettingsFile(Properties settings, String message) {
        try {
            FileOutputStream writer = new FileOutputStream("./settings/bmrsettings.properties");
            settings.store(writer, message);
        } catch (IOException e) {
            System.out.println("Couldn't write to file");
        }
    }

    private void setDefaultSettings() {
        try {
            Files.createFile(Paths.get("./settings/bmrsettings.properties"));
            FileOutputStream writer = new FileOutputStream("./settings/bmrsettings.properties");
            Properties generate_default = new Properties();
            generate_default.put("WeightUnit", DEFAULT_WEIGHT_UNIT);
            generate_default.put("HeightUnit", DEFAULT_HEIGHT_UNIT);
            generate_default.store(writer, "Default generated settings");
            writer.close();
        } catch (IOException e) {
            System.out.println("File already present");
        }
    }

    private void createAlert(String header, String content, Window main_stage) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Encoding error");
        error.setHeaderText(header);
        error.setContentText(content);
        error.initOwner(main_stage);
        error.showAndWait();
    }

    private boolean invalidInput(String current_char, String current_text) {
        return !current_char.matches("[0-9]|[,.]") || (current_char.matches("[,.]") && ((current_text.contains(",") || current_text.contains(".") || current_text.isEmpty())));
    }

    public static void main(String[] args) {
        launch();
    }
}