package ViewFX;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NoteFx extends StackPane {

    public NoteFx(String letter, int width, int height) {

        ImageView imageView = new ImageView(new Image(String.valueOf(getClass().getResource("img/notes/greencheckbox.png"))));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);

        Text decorationText = new Text(letter);
        decorationText.getStyleClass().add("decorationtext");

        TextArea textArea = new TextArea();
        textArea.setPromptText("Your notes here");
        textArea.setPrefSize(width, height);
        textArea.setMaxSize(width, height);
        textArea.setMinSize(width, height);

        switch (letter) {
            case "A":
            case "C":
                textArea.getStyleClass().add("A");
                break;
            case "B":
            case "D":
                textArea.getStyleClass().add("B");
                break;
            case "E":
                textArea.getStyleClass().add("E");
                break;
            case "F":
                textArea.getStyleClass().add("F");
                break;
        }

        textArea.setWrapText(true);

        textArea.getStyleClass().add("text-area");
        getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        textArea.setPadding(new Insets(42, 0, 42, 0));

        decorationText.setTranslateX(-0.5 * textArea.getMaxWidth() + 25);
        decorationText.setTranslateY(-0.5 * textArea.getMaxHeight() + 25);

        TextField textField = new TextField();
        textField.setPromptText("Final remark");
        textField.setMinSize(textArea.getMinWidth() - textArea.getMinWidth() / 4, textArea.getMinHeight() / 7);

        HBox belowrightPart = new HBox(imageView,textField);
        belowrightPart.setMaxSize(250, 25);
        belowrightPart.setSpacing(10);
        belowrightPart.setTranslateY(0.5 * textArea.getPrefHeight() - 25);
        belowrightPart.setAlignment(Pos.CENTER_RIGHT);

        getChildren().addAll(textArea, decorationText, belowrightPart);

        textArea.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                textArea.appendText(System.lineSeparator());
                e.consume();
            }
        });
    }
}
