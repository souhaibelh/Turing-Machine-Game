package ViewFX;

import Model.Code;
import GeneralMethods.FXUtils;
import Constants.NumericConstants;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class KeyboardFx extends HBox {
    ObservableList<Button> triangleButtons;
    ObservableList<Button> squareButtons;
    ObservableList<Button> circleButtons;
    private VBox triangleLayout;
    private VBox squareLayout;
    private VBox circleLayout;
    String toggleableCssClass;

    public KeyboardFx() {
        setToggleableCssClass("activeButton");
        prepareKeyboardLayouts();
        setSpacing(10);
        setMaxHeight(500);
        setMinHeight(NumericConstants.LABEL_HEIGHT.getValue() * 9 + NumericConstants.LABEL_BORDER_WIDTH.getValue() * 8 - 130);
        getChildren().addAll(triangleLayout, squareLayout, circleLayout);
    }

    public boolean allButtonsClicked() {
        return isTriangleButtonClicked() && isSquareButtonClicked() && isCircleButtonClicked();
    }

    public boolean isTriangleButtonClicked() {
        return isButtonClicked(triangleButtons);
    }

    public boolean isSquareButtonClicked() {
        return isButtonClicked(squareButtons);
    }

    public boolean isCircleButtonClicked() {
        return isButtonClicked(circleButtons);
    }

    private boolean isButtonClicked(ObservableList<Button> buttonList) {
        for (Button b : buttonList) {
            if (b.getStyleClass().contains("activeButton")) {
                return true;
            }
        }
        return false;
    }

    public void disableButtons() {
        disableButtonsList(triangleButtons);
        disableButtonsList(squareButtons);
        disableButtonsList(circleButtons);
    }

    public void enableButtons() {
        enableButtonsList(triangleButtons);
        enableButtonsList(squareButtons);
        enableButtonsList(circleButtons);
    }

    private void enableButtonsList(ObservableList<Button> buttonList) {
        for (Button b : buttonList) {
            b.setDisable(false);
        }
    }

    private void disableButtonsList(ObservableList<Button> buttonList) {
        for (Button b : buttonList) {
            b.setDisable(true);
        }
    }

    public void setToggleableCssClass(String newCssClass) {
        toggleableCssClass = newCssClass;
    }

    public ObservableList<Button> getTriangleButtons() {
        return triangleButtons;
    }

    public ObservableList<Button> getSquareButtons() {
        return squareButtons;
    }

    public ObservableList<Button> getCircleButtons() {
        return circleButtons;
    }

    private void prepareKeyboardLayouts() {
        StackPane triangle = new StackPane(FXUtils.createTriangle(40,40));
        StackPane square = new StackPane(FXUtils.createSquare(40));
        StackPane circle = new StackPane(FXUtils.createCircle(20));

        triangleButtons = FXUtils.createGameButtons(5);
        squareButtons = FXUtils.createGameButtons(5);
        circleButtons = FXUtils.createGameButtons(5);

        makeToggleableButtonStyles(triangleButtons);
        makeToggleableButtonStyles(squareButtons);
        makeToggleableButtonStyles(circleButtons);

        triangleLayout = new VBox(triangle);
        triangleLayout.getChildren().addAll(triangleButtons);

        squareLayout = new VBox(square);
        squareLayout.getChildren().addAll(squareButtons);

        circleLayout = new VBox(circle);
        circleLayout.getChildren().addAll(circleButtons);

        triangleLayout.setSpacing(20);
        squareLayout.setSpacing(20);
        circleLayout.setSpacing(20);

        triangleLayout.getStyleClass().add("layoutsCSS");
        squareLayout.getStyleClass().add("layoutsCSS");
        circleLayout.getStyleClass().add("layoutsCSS");

        getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));
    }

    private void makeToggleableButtonStyles(ObservableList<Button> shapeButtons) {
        for (Button b : shapeButtons) {
            b.addEventHandler(ActionEvent.ACTION, event -> {
                for (Button b2 : shapeButtons) {
                    b2.getStyleClass().remove("notclicked");
                    b2.getStyleClass().add("defaultbutton");
                    b2.getStyleClass().remove(toggleableCssClass);
                }
                b.getStyleClass().add(toggleableCssClass);
            });
        }
    }

    public void removeCssClass(ObservableList<Button> buttonsList, String className) {
        for (Button b : buttonsList) {
            b.getStyleClass().remove(className);
        }
    }

    public void clearButtonsSelections() {
        removeCssClass(circleButtons, toggleableCssClass);
        removeCssClass(squareButtons, toggleableCssClass);
        removeCssClass(triangleButtons, toggleableCssClass);
    }

    public void showNotClicked() {
        showNotClickedBtnList(circleButtons);
        showNotClickedBtnList(squareButtons);
        showNotClickedBtnList(triangleButtons);
    }

    private void showNotClickedBtnList(ObservableList<Button> buttons) {
        for (Button b : buttons) {
            if (!b.getStyleClass().contains("notclicked")) {
                b.getStyleClass().add("notclicked");
                b.getStyleClass().remove("defaultbutton");
            }
        }
    }

    public void hideNotClicked() {
        hideNotClickedBtnList(circleButtons);
        hideNotClickedBtnList(squareButtons);
        hideNotClickedBtnList(triangleButtons);
    }

    private void hideNotClickedBtnList(ObservableList<Button> buttons) {
        for (Button b : buttons) {
            if (b.getStyleClass().contains("notclicked")) {
                b.getStyleClass().remove("notclicked");
                b.getStyleClass().add("defaultbutton");
            }
        }
    }


    public String getFirstDigit() {
        for (Button b : triangleButtons) {
            if (b.getStyleClass().contains(toggleableCssClass)) {
                return b.getText();
            }
        }
        return null;
    }

    public String getSecondDigit() {
        for (Button b : squareButtons) {
            if (b.getStyleClass().contains(toggleableCssClass)) {
                return b.getText();
            }
        }
        return null;
    }

    public String getThirdDigit() {
        for (Button b : circleButtons) {
            if (b.getStyleClass().contains(toggleableCssClass)) {
                return b.getText();
            }
        }
        return null;
    }

    public void setCodeInKeyboard(Code currentCode) {
        clearButtonsSelections();
        if (currentCode != null) {
            Integer firstDigit = currentCode.getDigit(0);
            Integer secondDigit = currentCode.getDigit(1);
            Integer thirdDigit = currentCode.getDigit(2);
            System.out.println(firstDigit);
            System.out.println(triangleButtons.get(5-firstDigit).getText());
            triangleButtons.get(5-firstDigit).getStyleClass().add("activeButton");
            squareButtons.get(5-secondDigit).getStyleClass().add("activeButton");
            circleButtons.get(5-thirdDigit).getStyleClass().add("activeButton");
        }
    }
}
