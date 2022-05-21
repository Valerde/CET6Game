package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ykn.sovava.Director;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;
import ykn.sovava.Tools.WriteWA;

/**
 * Description: 用于游戏界面的更新
 *
 * @author: ykn
 * @date: 2022年05月21日 16:02
 **/
public abstract class GameSceneChange extends GameScene{
//    public Label label;//TODO 把这个改为Canvas
//    public TextField textField;
//    public Label labelResult;
//    public Label labelTranslation;
//    public Label playerInfo;
//    public Label scoreLabel;
//    public Button readyButton;
    public int myScore;
    public int otherScore;

    public GameSceneChange(Stage stage) {
        super(stage);
//        this.label = label;
//        this.textField = textField;
//        this.labelResult = labelResult;
//        this.labelTranslation = labelTranslation;
//        this.playerInfo = playerInfo;
//        this.scoreLabel = scoreLabel;
//        this.readyButton = readyButton;
        myScore = otherScore= 10;
    }
    public void set(WordsHandle wh, String s, String status) {
        if (status.equals(ScoreStatus.NO_ANSWER)) {
            WriteWA.writeLineFile(wh.getEnglish() + " | " + wh.getTranslation() + "\n");
            Platform.runLater(() -> {
                labelResult.setText("Pass");
                labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: yellow;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
            });
            otherScore -= 1;
        } else {
            otherScore = Integer.parseInt(s.split("-")[1] + "0") / 10;
        }
        myScore = Integer.parseInt(s.split("-")[0] + "0") / 10;
        label.setText(wh.getEnglishIncomplete());
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
        label.setLayoutY(0);
        label.setLayoutX(175 - label.getWidth() / 2);

    }

    public void set(WordsHandle wh) {
        label.setText(wh.getEnglishIncomplete());
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
        label.setLayoutY(0);
        label.setLayoutX(175 - label.getWidth() / 2);
    }

    public void set(Boolean flag) {
        Director.getInstance().gameOver(flag);
    }
}
