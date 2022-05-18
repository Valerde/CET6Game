package ykn.sovava;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @className: ykn.sovava.Main
 * @description:
 * @author: ykn
 * @date: 2022/5/18
 **/
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Director.getInstance().init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
