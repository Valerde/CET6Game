package ykn.sovava.scene;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @className: Index
 * @description:
 * @author: ykn
 * @date: 2022/5/18
 **/
public class Index {

    public static void load(Stage stage){
        try {
            Parent root = FXMLLoader.load(Index.class.getResource("/fxml/index.fxml"));
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
