package ViewFX;

import Model.Level;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameInfo extends VBox {
    private final Text score;
    private final Text rounds;
    private Level level;

    public GameInfo() {
        score = new Text();
        rounds = new Text();
        score.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 18; -fx-font-weight: 900; -fx-text-fill: orange");
        rounds.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 18; -fx-font-weight: 900; -fx-text-fill: orange");
        getChildren().addAll(score,rounds);
        setSpacing(20);
        setAlignment(Pos.CENTER);
    }

    public void setLevel(Level level) {
        Text levelText = new Text(level.toString().toUpperCase());
        levelText.setStyle("-fx-font-family: 'Roboto Light'; -fx-font-size: 18; -fx-font-weight: 900; -fx-text-fill: orange");
        getChildren().add(levelText);
    }

    public void setScore(int score) {
        this.score.setText("Score: " + String.valueOf(score).toUpperCase());
    }

    public void setRounds(int rounds) {
        this.rounds.setText("Rounds: " + String.valueOf(rounds).toUpperCase());
    }
}
