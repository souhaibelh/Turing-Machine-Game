package ViewFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ValidatorFx extends StackPane {
    private final ImageView imageView;

    public ValidatorFx(Image image) {
        imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setFitHeight(200);
        getChildren().add(imageView);
    }

    public ImageView getImage() {
        return imageView;
    }
}
