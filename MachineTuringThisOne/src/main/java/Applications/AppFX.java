package Applications;
import Model.Level;
import Model.TuringMachine;
import GeneralMethods.FileMethods;
import Constants.NumericConstants;
import ViewFX.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.IntStream;

public class AppFX extends Application {
    private TuringMachine model;
    private List<Level> levelsInformation;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize the facade
        levelsInformation = FileMethods.getLevels();
        model = new TuringMachine();
        LevelSelectorView levelSelectorView = new LevelSelectorView(levelsInformation);

        Text finalText = new Text();
        Scene secondaryScene = new Scene(new HBox(finalText));
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(secondaryScene);

        // Initialize the UI components needed for the View instance.
        KeyboardFx keyboardFx = new KeyboardFx();
        ButtonBar buttonBar = new ButtonBar();
        ValidatorResultView validatorView = new ValidatorResultView(6,6);
        CodeInputView roundsCodeView = new CodeInputView();
        GameInfo gameInfo = new GameInfo();
        ErrorText errorText = new ErrorText(NumericConstants.ERROR_TEXT_DISPLAY_TIME.getValue());
        ValidatorList listValidators = new ValidatorList();

        // Create the view
        GameView view = new GameView(roundsCodeView, keyboardFx, buttonBar, validatorView, errorText, gameInfo, listValidators);

        // Scenes used in the application (we switch between these)
        Scene levelSelectScene = new Scene(levelSelectorView);
        Scene gameScene = new Scene(view);

        // Setting logic for random level selecting
        levelSelectorView.getRandomLevel().addEventHandler(ActionEvent.ACTION, event -> {
            model.setGameLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
            gameInfo.setLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
            listValidators.setLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
            primaryStage.setScene(gameScene);
        });

        // Setting logic for level selecting
        levelSelectorView.getPlayButton().addEventHandler(ActionEvent.ACTION, event -> {
            if (levelSelectorView.isValidSelection()) {
                model.setGameLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
                primaryStage.setScene(gameScene);
                gameInfo.setLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
                listValidators.setLevel(getReadyLevel(levelSelectorView.getSelectedLevel()));
            } else {
                levelSelectorView.setErrorText("Select at least one level or randomize");
            }
        });

        // We view as an observer of model
        model.registerObserver(view);

        // Attach controller to model
        buttonBar.getOkButton().addEventHandler(ActionEvent.ACTION, event -> {
            model.addCode(
                    keyboardFx.getFirstDigit() + keyboardFx.getSecondDigit() + keyboardFx.getThirdDigit()
            );
        });

        buttonBar.getSkipRoundButton().addEventHandler(ActionEvent.ACTION, event -> {
            model.nextRound();
        });

        buttonBar.getCheckCodeButton().addEventHandler(ActionEvent.ACTION, event -> {
            model.checkCode();
        });

        buttonBar.getRedoButton().addEventHandler(ActionEvent.ACTION, event -> {
            model.redo();
        });

        buttonBar.getUndoButton().addEventHandler(ActionEvent.ACTION, event -> {
            model.undo();
        });

        IntStream.range(0, validatorView.getValidatorButtons().size()).forEach(
                i -> validatorView.getValidatorButtons().get(i).addEventHandler(ActionEvent.ACTION, event -> {
                    model.validate(i);
                })
        );

        // we start showing level select scene
        primaryStage.setScene(levelSelectScene);
        primaryStage.show();
        primaryStage.setResizable(true);
    }

    private Level getReadyLevel(int index) {
        Level level = levelsInformation.get(index);
        level.setVerifiers();
        return level;
    }
}
