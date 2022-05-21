package ykn.sovava;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Description: 程序入口
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Director.getInstance().init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
