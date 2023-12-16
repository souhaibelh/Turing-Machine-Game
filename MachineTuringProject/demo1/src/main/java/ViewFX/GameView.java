package ViewFX;

import Model.TuringMachineChangeEvent;
import ModelUtils.Observer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameView extends ScrollPane implements Observer {
    private final CodeInputView codeView;
    private final KeyboardFx keyboardFx;
    private final ButtonBar buttonBar;
    private final ValidatorResultView validatorView;
    private final ErrorText errorText;
    private final GameInfo gameInfo;
    private final IntegerProperty currentRound;

    /**
     * Creates the GameView
     *
     * @param codeView       codeViewPart
     * @param keyboardFx     keyboardFx part
     * @param buttonBar      buttonBar part
     * @param validatorView  validatorResultView
     * @param errorText      errorText
     * @param gameInfo       gameInfo
     * @param listValidators validatorList
     */
    public GameView(CodeInputView codeView, KeyboardFx keyboardFx, ButtonBar buttonBar,
                    ValidatorResultView validatorView, ErrorText errorText, GameInfo gameInfo, ValidatorList listValidators) {
        this.codeView = codeView;
        this.keyboardFx = keyboardFx;
        this.buttonBar = buttonBar;
        this.validatorView = validatorView;
        this.errorText = errorText;
        this.gameInfo = gameInfo;
        currentRound = new SimpleIntegerProperty(1);

        VBox container = new VBox(keyboardFx, this.buttonBar);
        this.buttonBar.setPadding(new Insets(0,0,0,15));
        container.setSpacing(20);
        container.setAlignment(Pos.CENTER);

        gameInfo.setScore(1);
        gameInfo.setRounds(1);
        VBox totalContainer = new VBox();
        setContent(totalContainer);
        totalContainer.getChildren().addAll(
                gameInfo,
                listValidators,
                new HBox(codeView, validatorView, container),
                errorText,
                new NoteList()
        );
        setContent(totalContainer);
        getStyleClass().remove("hidden-scroll");
        setFitToHeight(true);
        getStylesheets().add(getClass().getResource("scrollpane.css").toExternalForm());
        getStyleClass().add("noborderpane");


        connectKeyboardButtonsToCodeView();
        connectVScrollingValidatorViewToCodeView();
    }

    /**
     * Connects scrolling so when we scroll one the other scrolls, this way they scroll at the same time
     */
    private void connectVScrollingValidatorViewToCodeView() {
        validatorView.getVerticalScrollProperty().addListener((observable, oldValue, newValue) -> codeView.scrollLabels(newValue.doubleValue()));
        codeView.getVerticalScrollProperty().addListener((observable, oldValue, newValue) -> validatorView.scrollValidatorBoxes(newValue.doubleValue()));
    }

    /**
     * So when we type in keyboard it updates in codeview automatically
     */
    private void connectKeyboardButtonsToCodeView() {
        attachButtonHandlers(keyboardFx.getTriangleButtons(), codeView, 1);
        attachButtonHandlers(keyboardFx.getSquareButtons(), codeView, 2);
        attachButtonHandlers(keyboardFx.getCircleButtons(), codeView, 3);
    }

    /**
     * So we can handle digits in the view
     * @param buttons button list
     * @param roundsCodeView code view
     * @param digitIndex index of digit to draw
     */
    private void attachButtonHandlers(ObservableList<Button> buttons, CodeInputView roundsCodeView, int digitIndex) {
        for (Button button : buttons) {
            button.addEventHandler(ActionEvent.ACTION, event -> {
                roundsCodeView.setDigit(currentRound.get() - 1, digitIndex, button.getText());
                roundsCodeView.scrollLabels(1.0);
            });
        }
    }

    /**
     * Updates the view thanks to the model that communicates to it
     * @param eventChange informs of all changes
     */
    @Override
    public void update(TuringMachineChangeEvent eventChange) {
        if (eventChange.isDoneCommand()) {
            switch (eventChange.getDoneCommandType()) {
                case VALIDATE_VERIFIER:
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setScore(eventChange.getScore());
                    gameInfo.setRounds(currentRound.get());

                    int index = eventChange.getVerifierIndex();
                    boolean result = eventChange.getVerifierResult();
                    int position = currentRound.get() - 1;

                    validatorView.updateValidatorBox(position, result, index);
                    break;
                case ADD_CODE:
                    gameInfo.setScore(eventChange.getScore());
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setRounds(currentRound.get());

                    keyboardFx.setCodeInKeyboard(eventChange.getCurrentCode());
                    codeView.setCodeInView(eventChange.getCurrentCode(), currentRound.get() - 1);
                    keyboardFx.disableButtons();
                    buttonBar.disableOkButton();
                    buttonBar.enableCheckButton();
                    break;
                case NEXT_ROUND:
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setScore(eventChange.getScore());
                    gameInfo.setRounds(currentRound.get());

                    keyboardFx.clearButtonsSelections();
                    keyboardFx.enableButtons();
                    buttonBar.enableOkButton();
                    buttonBar.disableCheckButton();

                    if (currentRound.get() > 9) {
                        validatorView.addRow();
                        codeView.addRow();
                    }
                    break;
                case CHECK_CODE:
                    System.out.println(eventChange.isGameWon());
                    break;
            }
        } else if (eventChange.isUndoneCommand()){
            switch (eventChange.getUndoneCommandType()) {
                case VALIDATE_VERIFIER:
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setScore(eventChange.getScore());
                    gameInfo.setRounds(currentRound.get());

                    int index = eventChange.getVerifierIndex();
                    int position = currentRound.get() - 1;

                    validatorView.defaultValidatorBox(position, index);
                    break;
                case ADD_CODE:
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setScore(eventChange.getScore());
                    gameInfo.setRounds(currentRound.get());

                    keyboardFx.setCodeInKeyboard(eventChange.getCurrentCode());
                    codeView.clearLastLabels(currentRound.get() - 1);
                    keyboardFx.enableButtons();
                    buttonBar.enableOkButton();
                    buttonBar.disableCheckButton();
                    break;
                case NEXT_ROUND:
                    currentRound.set(eventChange.getCurrentRound());
                    gameInfo.setScore(eventChange.getScore());
                    gameInfo.setRounds(currentRound.get());

                    keyboardFx.clearButtonsSelections();
                    keyboardFx.disableButtons();
                    buttonBar.disableOkButton();
                    buttonBar.enableCheckButton();
                    keyboardFx.setCodeInKeyboard(eventChange.getCurrentCode());

                    if (currentRound.get() >= 9) {
                        validatorView.removeRow();
                        codeView.removeRow();
                    }
                    break;
                case CHECK_CODE:
                    break;
            }
        } else if (eventChange.isErrorCommand()) {
            errorText.setErrorText(eventChange.getErrorMessage());
            switch (eventChange.getErrorCommandType()) {
                case ADD_CODE:
                    if (!keyboardFx.allButtonsClicked()) {
                        Timeline timeline = new Timeline();
                        timeline.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> keyboardFx.showNotClicked()));
                        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> keyboardFx.hideNotClicked()));
                        timeline.playFromStart();
                    } else {
                        keyboardFx.clearButtonsSelections();
                        codeView.clearLastLabels(currentRound.get() - 1);
                    }
                    break;
                case NEXT_ROUND:
                    keyboardFx.clearButtonsSelections();
                    codeView.clearLastLabels(currentRound.get() - 1);
                    break;
            }
        }
    }
}
