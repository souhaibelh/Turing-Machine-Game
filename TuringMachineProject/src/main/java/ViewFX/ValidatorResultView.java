package ViewFX;

import Constants.ColorConstants;
import Constants.NumericConstants;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ValidatorResultView extends VBox {
    private final int LAYOUT_WIDTH = 410;
    private final String[] allLetters = {"A", "B", "C", "D", "E", "F"};
    private final ObservableList<ValidatorBox>[] columnBoxes;
    private final ObservableList<Button> validatorButtons;
    private final VBox[] columnBoxesContainers;
    private final int numberOfColumns;
    private final HBox lettersContainer;
    private ScrollPane boxesContainer;

    public ValidatorResultView(int columns, int usableColumns) {
        validatorButtons = FXCollections.observableArrayList();
        validatorButtons.addListener(new ListChangeListener<>() {
            @Override
            public void onChanged(Change<? extends Button> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        lettersContainer.getChildren().addAll(c.getAddedSubList());
                    }
                }
            }
        });
        lettersContainer = new HBox();
        this.numberOfColumns = columns;

        columnBoxesContainers = new VBox[this.numberOfColumns];
        columnBoxes = new ObservableList[this.numberOfColumns];

        prepareArrays();
        prepareLetters();
        prepareLayout();
        getChildren().addAll(
                lettersContainer,
                boxesContainer
        );
        lettersContainer.setSpacing(7);
        lettersContainer.setAlignment(Pos.CENTER);
        lettersContainer.setPadding(new Insets(0,3,0,5));
        setSpacing(18);
    }

    public ObservableList<Button> getValidatorButtons() {
        return validatorButtons;
    }

    public void scrollValidatorBoxes(double amount) {
        boxesContainer.setVvalue(amount);
    }

    public DoubleProperty getVerticalScrollProperty() {
        return boxesContainer.vvalueProperty();
    }

    private void initializeColumnBoxesContainers() {
        for (int i = 0; i< columnBoxesContainers.length; i++) {
            columnBoxesContainers[i] = new VBox();
        }
    }

    private void initializeColumnBoxesLists() {
        for (int i = 0; i<this.numberOfColumns; i++) {
            ObservableList<ValidatorBox> list = FXCollections.observableArrayList();
            columnBoxes[i] = list;
        }
    }

    /**
     * Adds new validator Boxes to the columns
     */
    private void generateValidatorBoxes() {
        for (ObservableList<ValidatorBox> ob : columnBoxes) {
            for (int i=0; i<9; i++) {
                ob.add(new ValidatorBox());
            }
        }
    }

    /**
     * Sets up listeners for added or removed and updates the parent node accordingly
     */
    private void setupColumnBoxesListener() {
        for (int i = 0; i < columnBoxes.length; i++) {
            int finalI = i;
            columnBoxes[i].addListener((ListChangeListener<ValidatorBox>) c -> {
                while (c.next()) {
                    if (c.wasRemoved()) {
                        columnBoxesContainers[finalI].getChildren().removeAll(c.getRemoved());
                    } else if (c.wasAdded()) {
                        columnBoxesContainers[finalI].getChildren().addAll(c.getAddedSubList());
                    }
                }
            });
        }
    }

    private void prepareArrays() {
        initializeColumnBoxesLists();
        initializeColumnBoxesContainers();
        generateValidatorBoxes();
        setupColumnBoxesListener();
    }

    /**
     * Prepares the letters of the column
     */
    private void prepareLetters() {
        for (int i = 0; i< numberOfColumns; i++) {
            Button letter = new Button(allLetters[i]);
            letter.setMaxHeight(30);
            letter.setMinHeight(30);
            letter.setPrefHeight(30);
            letter.getStyleClass().add("defaultbutton");
            letter.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            letter.setStyle("-fx-font-size: 30px");
            validatorButtons.add(letter);
        }
    }

    /**
     * prepares the game layout
     */
    private void prepareLayout() {
        for (int i=0; i<this.numberOfColumns; i++) {
            columnBoxesContainers[i].getChildren().addAll(columnBoxes[i]);
            columnBoxesContainers[i].setSpacing(19);
        }

        lettersContainer.setMaxHeight(60);
        lettersContainer.setMinHeight(60);
        lettersContainer.setMaxWidth(LAYOUT_WIDTH);
        lettersContainer.setMinWidth(LAYOUT_WIDTH);

        HBox hboxBoxes = new HBox();
        hboxBoxes.getChildren().addAll(
                columnBoxesContainers
        );
        hboxBoxes.setSpacing(30);
        hboxBoxes.setAlignment(Pos.CENTER);
        hboxBoxes.setPadding(new Insets(9.5,0,9.5,8));
        hboxBoxes.setMaxWidth(LAYOUT_WIDTH);
        hboxBoxes.setMinWidth(LAYOUT_WIDTH);

        boxesContainer = new ScrollPane();
        hboxBoxes.heightProperty().addListener((observable, oldValue, newValue) -> boxesContainer.setVvalue(1.0));

        boxesContainer.setMaxWidth(LAYOUT_WIDTH + 7);
        boxesContainer.setMinWidth(LAYOUT_WIDTH + 7);
        boxesContainer.setMaxHeight(NumericConstants.LABEL_HEIGHT.getValue() * 9 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 8 + 10);
        boxesContainer.setMinHeight(NumericConstants.LABEL_HEIGHT.getValue() * 9 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 8 + 10);
        boxesContainer.setContent(hboxBoxes);
        boxesContainer.getStylesheets().add(getClass().getResource("scrollpane.css").toExternalForm());
        boxesContainer.getStyleClass().add("visible-scroll");
        boxesContainer.getStyleClass().add("noborderpane");
    }

    /**
     * adds a row
     */
    public void addRow() {
        for (ObservableList<ValidatorBox> b : columnBoxes) {
            b.add(new ValidatorBox());
        }
    }

    /**
     * updates validator box to look like correct result or bad result
     * @param position of validator box
     * @param valid is it valid or no
     * @param validatorIndex index of validator
     */
    public void updateValidatorBox(int position, boolean valid, int validatorIndex) {
        ValidatorBox box = columnBoxes[validatorIndex].get(position);
        if (valid) {
            box.setImageView(new Image(String.valueOf(getClass().getResource("img/notes/whitecheck.png"))));
            box.setRectangleColor(ColorConstants.RIGHT.getColorCode());
        } else {
            box.setImageView(new Image(String.valueOf(getClass().getResource("img/notes/whitewrongcheck.png"))));
            box.setRectangleColor(ColorConstants.WRONG.getColorCode());
        }
    }

    /**
     * sets the validator box to default, useful on undo redo
     * @param position of the box
     * @param index of the box
     */
    public void defaultValidatorBox(int position, int index) {
        columnBoxes[index].get(position).setDefault();
    }

    /**
     * removes a row
     */
    public void removeRow() {
        for (ObservableList<ValidatorBox> b : columnBoxes) {
            b.remove(b.size() - 1);
        }
    }
}
