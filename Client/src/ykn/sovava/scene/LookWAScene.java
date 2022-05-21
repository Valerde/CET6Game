package ykn.sovava.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @className: LookWAScene
 * @description:
 * @author: ykn
 * @date: 2022/5/21
 **/
public class LookWAScene {
    public static void load(Stage stage){
        try {
            Parent root = FXMLLoader.load(Index.class.getResource("/fxml/lookwa.fxml"));
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
