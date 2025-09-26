package ViewFX;

import Constants.ColorConstants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class ErrorText extends HBox {
    private final Text errorText;
    private final Timeline displayer;

    public ErrorText(int secondsToShow) {
        setVisible(false); // hide the label
        errorText = new Text();
        errorText.setStyle("-fx-font-family: 'Arial Black'; -fx-font-size: 22px");
        errorText.setFill(Paint.valueOf(ColorConstants.WRONG.getColorCode()));

        displayer = new Timeline();
        displayer.getKeyFrames().add(new KeyFrame(Duration.ZERO, event -> setVisible(true))); // start from here, we make it visible
        displayer.getKeyFrames().add(new KeyFrame(Duration.seconds(5), event -> {
            setVisible(false);
        })); // after 5 seconds we make it invisible
        displayer.setCycleCount(1);

        ValidatorBox incorrectBox = new ValidatorBox();
        incorrectBox.setImageView(new Image(String.valueOf(getClass().getResource("img/notes/whitewrongcheck.png"))));
        incorrectBox.setRectangleColor(ColorConstants.WRONG.getColorCode());
        getChildren().addAll(incorrectBox, errorText);
        setMaxHeight(50);
        setMinHeight(50);
        setAlignment(Pos.CENTER);
        setSpacing(10);
    }

    /**
     * Sets a error text and displays it
     * @param newErrorText
     */
    public void setErrorText(String newErrorText) {
        errorText.setText(newErrorText);
        displayer.playFromStart(); // we start the displayer
    }
}
