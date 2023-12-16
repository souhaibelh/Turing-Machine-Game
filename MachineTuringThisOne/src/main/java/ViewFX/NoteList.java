package ViewFX;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;

public class NoteList extends VBox {

    public NoteList() {
        NoteFx noteA = new NoteFx("A", 400, 200);
        NoteFx noteB = new NoteFx("B", 400, 200);
        NoteFx noteC = new NoteFx("C", 400, 200);
        NoteFx noteD = new NoteFx("D", 400, 200);
        NoteFx noteE = new NoteFx("E", 400, 200);
        NoteFx noteF = new NoteFx("F", 400, 200);

        setAlignment(Pos.CENTER);
        getChildren().addAll(
                new HBox(noteA,noteB),
                new HBox(noteC,noteD),
                new HBox(noteE,noteF)
        );
    }
}
