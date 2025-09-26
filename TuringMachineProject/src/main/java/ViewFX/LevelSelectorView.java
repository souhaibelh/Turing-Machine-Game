package ViewFX;

import Model.Level;
import GeneralMethods.MathMethods;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;

public class LevelSelectorView extends VBox {

    private final List<Level> levelsList;
    private Button randomLevel;
    private Button playButton;
    private final IntegerProperty selectedLevel;
    private final ObservableList<HBox> rows;
    private final ErrorText errorText;

    public LevelSelectorView(List<Level> levelsList) {
        errorText = new ErrorText(1);
        rows = FXCollections.observableArrayList();
        selectedLevel = new SimpleIntegerProperty(-1);
        randomLevel = new Button("Randomize");
        this.playButton = new Button("Play Now !");
        playButton.getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));
        playButton.getStyleClass().add("playNowButton");
        playButton.setAlignment(Pos.CENTER);
        randomLevel.getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));
        randomLevel.getStyleClass().add("playNowButton");
        randomLevel.setAlignment(Pos.CENTER);
        this.levelsList = levelsList;
        HBox columnTitlesContainer = new HBox();
        columnTitlesContainer.getChildren().addAll(
                getStyledText("LEVEL"),
                getStyledText("DIFFICULTY"),
                getStyledText("LUCK")
        );
        Text title = new Text("CHOOSE A LEVEL");
        title.setStyle("-fx-font-size: 50px; -fx-font-family: 'Roboto Light'; -fx-fill: black; -fx-font-weight: 900");
        HBox titleContainer = new HBox(title);
        titleContainer.setAlignment(Pos.CENTER);
        columnTitlesContainer.setSpacing(12);
        columnTitlesContainer.setAlignment(Pos.CENTER);
        HBox columnsContainer = populateColumns();
        getChildren().addAll(
                titleContainer,
                columnTitlesContainer,
                columnsContainer
        );
        VBox buttonsContainer = new VBox(randomLevel, playButton);
        buttonsContainer.setSpacing(10);
        buttonsContainer.setAlignment(Pos.CENTER);
        getChildren().addAll(buttonsContainer,errorText);
        setAlignment(Pos.CENTER);
        setSpacing(10);

        randomLevel.addEventHandler(ActionEvent.ACTION, event -> {
            selectedLevel.set(MathMethods.getRandomNumber(16));
            System.out.println(isValidSelection());
        });

        playButton.addEventHandler(ActionEvent.ACTION, event -> {
            for (HBox row : rows) {
                if (row.getStyleClass().contains("clickedLevel")) {
                    selectedLevel.set(rows.indexOf(row));
                }
            }
        });
    }

    public void setErrorText(String error) {
        this.errorText.setErrorText(error);
    }

    public Button getRandomLevel() {
        return this.randomLevel;
    }

    public boolean isValidSelection() {
        return selectedLevel.get() != -1;
    }

    public Integer getSelectedLevel() {
        return selectedLevel.get();
    }

    private HBox populateColumns() {
        VBox leftColumn = new VBox();
        VBox rightColumn = new VBox();

        leftColumn.setSpacing(10);
        rightColumn.setSpacing(10);
        leftColumn.setAlignment(Pos.CENTER);
        rightColumn.setAlignment(Pos.CENTER);

        int half = (int) Math.ceil(levelsList.size() / 2.0);

        for (int i = 0; i < levelsList.size(); i++) {
            Level l = levelsList.get(i);
            HBox row = new HBox();
            row.getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));
            row.addEventHandler(ActionEvent.ACTION, event -> {
                for (HBox row2 : rows) {
                    row2.getStyleClass().remove("clickedLevel");
                }
                if (!row.getStyleClass().contains("clickedLevel")) {
                    row.getStyleClass().add("clickedLevel");
                }
            });

            Parent bar = getBar(4, l.getDifficulty());
            StackPane luck = getStyledText(String.valueOf(l.getLuck()));
            Button btn = new Button(String.valueOf(l.getNum()));
            StackPane btnContainer = new StackPane(btn);

            if (l.getNum() >= 10) {
                btnContainer.setPadding(new Insets(0,0,0,-4));
            }

            row.getChildren().addAll(
                    btnContainer,
                    bar,
                    luck
            );
            row.setMaxWidth(300);
            row.setMinWidth(300);
            row.setSpacing(60);
            row.setAlignment(Pos.CENTER);

            rows.add(row);

            if (i < half) {
                leftColumn.getChildren().add(row);
            } else {
                rightColumn.getChildren().add(row);
            }
        }

        HBox columnsContainer = new HBox(leftColumn, rightColumn);
        columnsContainer.setSpacing(50);
        columnsContainer.setAlignment(Pos.CENTER);

        return columnsContainer;
    }


    public Button getPlayButton() {
        return this.playButton;
    }

    private StackPane getStyledText(String text) {
        Text Text = new Text(text);
        Text.setStyle("-fx-font-size: 22px; -fx-font-family: 'Roboto Light'; -fx-fill: #2db563; -fx-font-weight: 900");
        StackPane pane = new StackPane();
        pane.getChildren().add(Text);
        pane.setAlignment(Pos.CENTER);
        return pane;
    }

    private Parent getBar(int length, int nbpainted) {
        HBox barContainer = new HBox();
        barContainer.setSpacing(5);
        for (int i=0; i<length; i++) {
            Rectangle rectangle = new Rectangle(15,15);
            rectangle.setStroke(Color.valueOf("#2db563"));
            rectangle.setStrokeWidth(2);
            if (i >= nbpainted) {
                rectangle.setFill(null);
            } else {
                rectangle.setFill(Color.valueOf("#2db563"));
            }
            barContainer.getChildren().add(rectangle);
        }
        barContainer.setAlignment(Pos.CENTER);
        return barContainer;
    }

    public void resetSelection() {
        this.selectedLevel.set(-1);
    }
}
