package ViewFX;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class ValidatorBox extends StackPane {
    private final ImageView imageView;
    private final Rectangle rectangle;

    public ValidatorBox() {
        rectangle = new Rectangle(30,30, Color.TRANSPARENT);
        imageView = new ImageView();

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(20);

        rectangle.setArcHeight(10);
        rectangle.setArcWidth(10);
        rectangle.setStroke(Color.valueOf("#2db563"));
        rectangle.setStrokeWidth(3);
        rectangle.setFill(Color.WHITE);

        getChildren().addAll(rectangle,imageView);
        setAlignment(Pos.CENTER);
    }

    /**
     * Changes image of validator box
     * @param image
     */
    public void setImageView(Image image) {
        imageView.setImage(image);
    }

    /**
     * Changes color of validator box
     * @param color
     */
    public void setRectangleColor(String color) {
        rectangle.setFill(Paint.valueOf(color));
        rectangle.setStroke(Color.valueOf(color));
        rectangle.setStrokeWidth(3);
    }

    /**
     * Sets validator box to default
     */
    public void setDefault() {
        imageView.setImage(null);
        rectangle.setStroke(Color.valueOf("#2db563"));
        rectangle.setFill(Color.WHITE);
    }
}
