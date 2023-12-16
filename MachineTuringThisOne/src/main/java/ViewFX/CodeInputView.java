package ViewFX;
import Model.Code;
import GeneralMethods.FXUtils;
import Constants.NumericConstants;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

public class CodeInputView extends VBox {
    private final String TRIANGLE_CSS_CLASSNAME = "triangleLabels";
    private final String SQUARE_CSS_CLASSNAME = "squareLabels";
    private final String CIRCLE_CSS_CLASSNAME = "circleLabels";
    ObservableList<Label> triangleLabels;
    ObservableList<Label> squareLabels;
    ObservableList<Label> circleLabels;
    private HBox shapesContainer;
    private ScrollPane labelsContainer;

    public CodeInputView() {
        prepareLayouts();
        getChildren().addAll(
                shapesContainer,
                labelsContainer
        );
        setSpacing(18);
    }

    public void scrollLabels(double amount) {
        labelsContainer.setVvalue(amount);
    }

    public DoubleProperty getVerticalScrollProperty() {
        return labelsContainer.vvalueProperty();
    }


    private void prepareLayouts() {
        Shape triangle = FXUtils.createTriangle(40,40);
        Shape square = FXUtils.createSquare(40);
        Shape circle = FXUtils.createCircle(20);

        shapesContainer = new HBox();
        shapesContainer.getChildren().addAll(
                new Group(triangle),
                new Group(square),
                new Group(circle)
        );
        shapesContainer.setMaxWidth((NumericConstants.LABEL_WIDTH.getValue()) * 3 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 3);
        shapesContainer.setMinWidth((NumericConstants.LABEL_WIDTH.getValue()) * 3 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 3);
        shapesContainer.setMaxHeight(60);
        shapesContainer.setMinHeight(60);
        shapesContainer.setSpacing(20);
        shapesContainer.setAlignment(Pos.CENTER_LEFT);
        shapesContainer.setPadding(new Insets(0,0,0,10));

        triangleLabels = FXUtils.getLabels(TRIANGLE_CSS_CLASSNAME, 9);
        squareLabels = FXUtils.getLabels(SQUARE_CSS_CLASSNAME, 9);
        circleLabels = FXUtils.getLabels(CIRCLE_CSS_CLASSNAME, 9);

        VBox triangleLabelsContainer = new VBox();
        triangleLabelsContainer.getChildren().addAll(triangleLabels);

        VBox squareLabelsContainer = new VBox();
        squareLabelsContainer.getChildren().addAll(squareLabels);

        VBox circleLabelsContainer = new VBox();
        circleLabelsContainer.getChildren().addAll(circleLabels);

        addLabelListener(triangleLabels, triangleLabelsContainer, TRIANGLE_CSS_CLASSNAME);
        addLabelListener(squareLabels, squareLabelsContainer, SQUARE_CSS_CLASSNAME);
        addLabelListener(circleLabels, circleLabelsContainer, CIRCLE_CSS_CLASSNAME);

        HBox hboxLabels = new HBox();
        hboxLabels.getChildren().addAll(
                triangleLabelsContainer,
                squareLabelsContainer,
                circleLabelsContainer
        );

        labelsContainer = new ScrollPane();
        labelsContainer.setMaxWidth(NumericConstants.LABEL_WIDTH.getValue() * 3 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 3);
        labelsContainer.setMinWidth(NumericConstants.LABEL_WIDTH.getValue() * 3 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 3);
        labelsContainer.setMaxHeight(NumericConstants.LABEL_HEIGHT.getValue() * 9 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 8 + 10);
        labelsContainer.setMinHeight(NumericConstants.LABEL_HEIGHT.getValue() * 9 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 8 + 10);
        labelsContainer.setContent(hboxLabels);
        labelsContainer.getStylesheets().add(getClass().getResource("scrollpane.css").toExternalForm());
        labelsContainer.getStyleClass().add("hidden-scroll");
        labelsContainer.getStyleClass().add("noborderpane");
    }

    private void addLabelListener(ObservableList<Label> shapeLabels, VBox shapeLabelsContainer, String shapeCssClass) {
        shapeLabels.addListener((ListChangeListener<Label>) c -> {
            while (c.next()) {
                if (c.wasUpdated()) {
                    ((Label) shapeLabelsContainer.getChildren().get(shapeLabelsContainer.getChildren().size() - 1)).setText("");
                } else {
                    if (c.wasRemoved()) {
                        shapeLabelsContainer.getChildren().removeAll(c.getRemoved());
                        shapeLabelsContainer.getChildren().get(shapeLabelsContainer.getChildren().size() - 1).getStyleClass().add("B" + shapeCssClass);
                        shapeLabelsContainer.getChildren().get(shapeLabelsContainer.getChildren().size() - 1).getStyleClass().remove(shapeCssClass);
                    } else if (c.wasAdded()) {
                        shapeLabelsContainer.getChildren().addAll(c.getAddedSubList());
                        shapeLabelsContainer.getChildren().get(shapeLabelsContainer.getChildren().size() - 2).getStyleClass().remove("B" + shapeCssClass);
                        shapeLabelsContainer.getChildren().get(shapeLabelsContainer.getChildren().size() - 2).getStyleClass().add(shapeCssClass);
                    }
                }
            }
        });
    }

    public void setDigit(int position, int digitIndex, String digit) {
        switch (digitIndex) {
            case 1:
                triangleLabels.get(position).setText(digit);
                break;
            case 2:
                squareLabels.get(position).setText(digit);
                break;
            case 3:
                circleLabels.get(position).setText(digit);
                break;
        }
    }

    public void setCodeInView(Code currentCode, int position) {
        if (currentCode != null) {
            triangleLabels.get(position).setText(String.valueOf(currentCode.getDigit(0)));
            squareLabels.get(position).setText(String.valueOf(currentCode.getDigit(1)));
            circleLabels.get(position).setText(String.valueOf(currentCode.getDigit(2)));
        } else {
            clearLastLabels(0);
        }
    }

    public void clearLastLabels(int i) {
        System.out.println(i);
        for (int j=i; j<triangleLabels.size(); j++) {
            triangleLabels.get(j).setText("");
            squareLabels.get(j).setText("");
            circleLabels.get(j).setText("");
        }
    }

    public void addRow() {
        triangleLabels.add(FXUtils.getLastLabel(TRIANGLE_CSS_CLASSNAME));
        squareLabels.add(FXUtils.getLastLabel(SQUARE_CSS_CLASSNAME));
        circleLabels.add(FXUtils.getLastLabel(CIRCLE_CSS_CLASSNAME));
    }

    public void removeRow() {
        triangleLabels.remove(triangleLabels.size() - 1);
        squareLabels.remove(squareLabels.size() - 1);
        circleLabels.remove(circleLabels.size() - 1);
    }
}
