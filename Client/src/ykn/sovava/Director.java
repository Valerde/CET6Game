package ykn.sovava;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ykn.sovava.scene.GameScene;
import ykn.sovava.scene.Index;

/**
 * className: ykn.sovava.Director
 * dvsd
 * author: ykn
 * date: 2022/5/18
 **/
public class Director {
    public static final double HEIGHT = 800, WIDTH = 640;

    private static Director instance = new Director();

    private Stage stage = null;

    private GameScene gameScene = new GameScene();

    private Director() {
    }

    public static Director getInstance() {
        return instance;
    }

    public void init(Stage stage) {
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("CET6 GAME");
        stage.getIcons().add(new Image("/image/logo.jpg"));
        stage.setHeight(HEIGHT);
        stage.setWidth(WIDTH);
        stage.setResizable(false);
        this.stage = stage;

        toIndex();
        stage.show();
    }


    public void toIndex(){
        Index.load(stage);
    }

    public void gameStart() {
        gameScene.init(stage);
    }
}
