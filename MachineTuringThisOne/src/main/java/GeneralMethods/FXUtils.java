package GeneralMethods;
import Constants.NumericConstants;
import ViewFX.GameView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

/**
 * This class has some methods used in almost every JavaFX class I've made.
 */
public class FXUtils {

    /**
     * This method here takes in a css class name, it creates a new Label with the Label default sizes set in the
     * enum NumericConstants, then it sets its position to the center, and it adds to the label the css file corresponding
     * to it, and it adds to it the class we inputted in parameter
     * @param classNameCss css class name that the label will have
     * @return a created label with default style
     */
    public static Label getLabel(String classNameCss) {
        Label label = new Label();
        label.setMaxWidth(NumericConstants.LABEL_WIDTH.getValue());
        label.setMinWidth(NumericConstants.LABEL_WIDTH.getValue());
        label.setPrefWidth(NumericConstants.LABEL_WIDTH.getValue());
        label.setMaxHeight(NumericConstants.LABEL_HEIGHT.getValue());
        label.setMinHeight(NumericConstants.LABEL_HEIGHT.getValue());
        label.setPrefHeight(NumericConstants.LABEL_HEIGHT.getValue());
        label.setAlignment(Pos.CENTER);
        label.getStylesheets().add(String.valueOf(GameView.class.getResource("labels.css")));
        label.getStyleClass().add(classNameCss);
        return label;
    }

    /**
     * This method here returns a Label set to be the last label, last labels have kinda of a different style as in
     * they don't have bottom borders, the way to set it is by adding the B + css class name (this is a pattern made by me
     * to make it easier to style).
     * @param classNameCss css class name of the default label (it will add a B to it in the start so the css file knows
     *                     it's the last label we are talking about)
     * @return a label with css as if it was the last one (no bottom borders)
     */
    public static Label getLastLabel(String classNameCss) {
        return getLabel("B" + classNameCss);
    }

    /**
     * Returns a full ObsevableList of labels, it also takes in consideration the last one and gets it from the method
     * above, if it isn't a last one then it gets it from the method getLabel() which returns a defaulted label.
     * @param classNameCss css class of the labels
     * @param quantity amount of labels we want in the observable list
     * @return returns the observable list
     */
    public static ObservableList<Label> getLabels(String classNameCss, int quantity) {
        ObservableList<Label> labels = FXCollections.observableArrayList();
        for (int i=0; i<quantity; i++) {
            Label label = i == quantity - 1 ? getLastLabel(classNameCss) : getLabel(classNameCss);
            labels.add(label);
        }
        return labels;
    }

    /**
     * This method here creates a default triangle shape used throughout the JavaFX view class, the triangle
     * is rotated
     * @param width width of our triangle
     * @param height height of our triangle
     * @return the triangle with its default style applied
     */
    public static Polygon createTriangle(double width, double height) {
        Polygon triangle = new Polygon();
        // defines the vertices of the triangle, from 0 to 0 from width to 0 and from width/2 to height, equilateral triangle
        triangle.getPoints().addAll(0.0, 0.0, width, 0.0, width / 2, height);
        triangle.setRotate(180);
        triangle.setStyle("-fx-border-radius: 12px");
        triangle.setFill(Color.web("#58b3da"));
        return triangle;
    }

    /**
     * This method here creates a default square shape used throughout the JavaFX view class, the square
     * has arcHeight and arcWidth to make its corners a bit rounder
     * @param size size of one of the sides of the square
     * @return the square with its default style applied
     */
    public static Rectangle createSquare(double size) {
        Rectangle square = new Rectangle(size,size);
        square.setFill(Color.web("#febb12"));
        square.setArcHeight(10);
        square.setArcWidth(10);
        return square;
    }

    /**
     * This method here creates a default circle shape used throughout the JavaFX view class, the circle
     * is fairly simple, it only has a radius and some css default styles.
     * @param radius the radius of the circle
     * @return the circle with its default style applied
     */
    public static Circle createCircle(double radius) {
        Circle circle = new Circle(radius);
        circle.setFill(Color.web("#7f66ad"));
        return circle;
    }

    /**
     * This method here returns the buttons used in the keyboard, simple buttons from 5 to 1 in our case, or
     * custom ones if u we decide to give it a different quantity.
     * @param quantity quantity of buttons to generate
     * @return ObservableList containing the buttons
     */
    public static ObservableList<Button> createGameButtons(int quantity) {
        ObservableList<Button> buttons = FXCollections.observableArrayList();
        for (int i=quantity; i>0; i--) {
            Button button = new Button(String.valueOf(i));
            button.getStyleClass().add("defaultbutton");
            buttons.add(button);
        }
        return buttons;
    }
}
