package ViewFX;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The {@code ButtonBar} class represents a bar containing various buttons used in the game.
 * It extends {@code VBox} to arrange buttons vertically.
 */
public class ButtonBar extends VBox {

    private final int BUTTON_SIZE = 60;
    private final Button okButton;
    private final Button checkCodeButton;
    private final Button skipRoundButton;
    private final Button undoButton;
    private final Button redoButton;

    /**
     * Constructs a new {@code ButtonBar} with OK, Check Code, Skip Round, Undo, and Redo buttons. Initializes button
     * styles, sizes, and disables the Check Code button by default.
     */
    public ButtonBar() {
        okButton = new Button("OK");
        undoButton = new Button("<-");
        redoButton = new Button("->");
        checkCodeButton = new Button("?");
        skipRoundButton = new Button(">");

        checkCodeButton.setDisable(true);

        okButton.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20px");
        undoButton.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20px");
        redoButton.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 20px");

        checkCodeButton.setStyle("-fx-font-family: 'Arial Black'");
        skipRoundButton.setStyle("-fx-font-family: 'Arial Black'");

        okButton.getStyleClass().add("defaultbutton");
        checkCodeButton.getStyleClass().add("defaultbutton");
        skipRoundButton.getStyleClass().add("defaultbutton");
        redoButton.getStyleClass().add("defaultbutton");
        undoButton.getStyleClass().add("defaultbutton");

        okButton.setMaxWidth(BUTTON_SIZE);
        okButton.setMinWidth(BUTTON_SIZE);
        okButton.setMaxHeight(BUTTON_SIZE);
        okButton.setMinHeight(BUTTON_SIZE);

        undoButton.setMinHeight(BUTTON_SIZE);
        undoButton.setMinHeight(BUTTON_SIZE);
        undoButton.setMinHeight(BUTTON_SIZE);
        undoButton.setMinHeight(BUTTON_SIZE);

        redoButton.setMinHeight(BUTTON_SIZE);
        redoButton.setMinHeight(BUTTON_SIZE);
        redoButton.setMinHeight(BUTTON_SIZE);
        redoButton.setMinHeight(BUTTON_SIZE);

        checkCodeButton.setMaxWidth(BUTTON_SIZE);
        checkCodeButton.setMinWidth(BUTTON_SIZE);
        checkCodeButton.setMinHeight(BUTTON_SIZE);
        checkCodeButton.setMaxHeight(BUTTON_SIZE);

        skipRoundButton.setMaxWidth(BUTTON_SIZE);
        skipRoundButton.setMinWidth(BUTTON_SIZE);
        skipRoundButton.setMinHeight(BUTTON_SIZE);
        skipRoundButton.setMaxHeight(BUTTON_SIZE);

        getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        setMaxHeight(60);
        setMinHeight(60);
        setMaxWidth(218);
        setMinWidth(218);
        setSpacing(10);
        setAlignment(Pos.CENTER);

        HBox topButtonContainer = new HBox();
        topButtonContainer.getChildren().addAll(skipRoundButton, checkCodeButton, okButton);

        HBox bottomButtonContainer = new HBox();
        bottomButtonContainer.getChildren().addAll(undoButton, redoButton);

        getChildren().addAll(
                topButtonContainer,
                bottomButtonContainer
        );
    }

    /**
     * Gets the OK button.
     *
     * @return The OK button.
     */
    public Button getOkButton() {
        return okButton;
    }

    /**
     * Gets the Check Code button.
     *
     * @return The Check Code button.
     */
    public Button getCheckCodeButton() {
        return checkCodeButton;
    }

    /**
     * Gets the Skip Round button.
     *
     * @return The Skip Round button.
     */
    public Button getSkipRoundButton() {
        return skipRoundButton;
    }

    /**
     * Gets the Undo button.
     *
     * @return The Undo button.
     */
    public Button getUndoButton() {
        return undoButton;
    }

    /**
     * Gets the Redo button.
     *
     * @return The Redo button.
     */
    public Button getRedoButton() {
        return redoButton;
    }

    /**
     * Enables the OK button.
     */
    public void enableOkButton() {
        okButton.setDisable(false);
    }

    /**
     * Disables the OK button.
     */
    public void disableOkButton() {
        okButton.setDisable(true);
    }

    /**
     * Enables the Check Code button.
     */
    public void enableCheckButton() {
        checkCodeButton.setDisable(false);
    }

    /**
     * Disables the Check Code button.
     */
    /**
     * Disables the Check Code button.
     */
    public void disableCheckButton() {
        checkCodeButton.setDisable(true);
    }
}

