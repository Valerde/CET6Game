package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
public abstract class GameSceneChange extends GameScene {
    public int myScore;
    public int otherScore;
    public double Y = 20;
    public double speed = 1.1;
    public GameSceneChange(Stage stage) {
        super(stage);
        myScore = otherScore = 10;
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
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
    }

    public void set(WordsHandle wh) {
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
    }

    public void set(Boolean flag) {
        Director.getInstance().gameOver(flag);
    }

    /**
     * Description: 每帧绘图
     * @author: ykn
     * @date: 2022/5/22 0:24
     * @param s: incomplete
     * @return: void
     */
    public void paint( String s) {
        graphicsContext.drawImage(new Image("/image/background.png"),0,0,350,700);
        graphicsContext.drawImage(new Image("/image/border.png"),50,Y-42,260,71);
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setFont(new Font(30));
        graphicsContext.fillText(s, 95, Y);
        speed+=0.0002;
        Y += speed;
        //System.out.println(speed);
    }
}
