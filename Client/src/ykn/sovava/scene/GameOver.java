package ykn.sovava.scene;

import javafx.application.Platform;
import ykn.sovava.controller.OverController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * description: 游戏结束加载界面
 * @className: GameOver
 * @author: ykn
 * @date: 2022/5/18
 **/
public class GameOver {

    public static void load(Stage stage, boolean success){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Index.class.getResource("/fxml/gameover.fxml"));
            Parent root = fxmlLoader.load();
            OverController OverController = fxmlLoader.getController();
            if (success){//预设加载失败界面,如果游戏胜利则更改配图
                OverController.flagSuccess();
            }
            Platform.runLater(()->{
                stage.getScene().setRoot(root);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
