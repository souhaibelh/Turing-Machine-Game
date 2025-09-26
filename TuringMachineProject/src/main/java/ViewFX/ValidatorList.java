package ViewFX;

import Model.Level;
import Model.TuringMachineVerifier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class ValidatorList extends VBox {

    public ValidatorList() {
    }

    public void setLevel(Level level) {
        for (int i = 0; i < level.getVerifiers().size(); i += 2) {
            if (i + 1 < level.getVerifiers().size()) {
                ValidatorFx vImg = new ValidatorFx(new Image(String.valueOf(getClass().getResource("img/validators/card" +
                        level.getVerifiers().get(i).getId() + ".png"))));
                ValidatorFx vImg2 = new ValidatorFx(new Image(String.valueOf(getClass().getResource("img/validators/card" +
                        level.getVerifiers().get(i+1).getId() + ".png"))));
                HBox containers = new HBox();
                containers.setAlignment(Pos.CENTER);
                containers.getChildren().addAll(vImg,vImg2);
                containers.setSpacing(20);
                getChildren().add(containers);
            } else {
                ValidatorFx vImg = new ValidatorFx(new Image(String.valueOf(getClass().getResource("img/validators/card" +
                        level.getVerifiers().get(i).getId() + ".png"))));
                HBox container = new HBox();
                container.getChildren().add(vImg);
                getChildren().add(container);
            }
        }
    }
}
