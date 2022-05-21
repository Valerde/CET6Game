package ykn.sovava;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ykn.sovava.scene.*;

/**
 * description:导演类
 *
 * @author: ykn
 * @date: 2022/5/18
 **/
public class Director {
    public static final double HEIGHT = 800, WIDTH = 640;

    private static Director instance = new Director();

    private Stage stage = null;

    private MyClient client = null;

    private Director() {
    }

    public static Director getInstance() {
        return instance;
    }

    /**
     * Description: 布局和画布舞台的初始化
     *
     * @param stage:
     * @author: ykn
     * @date: 2022/5/21 13:56
     * @return: void
     */
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

    /**
     * Description: 启动初始界面
     *
     * @author: ykn
     * @date: 2022/5/21 13:57
     * @return: void
     */
    public void toIndex() {
        Index.load(stage);
    }

    /**
     * Description: 启动游戏界面
     *
     * @author: ykn
     * @date: 2022/5/21 13:58
     * @return: void
     */
    public void gameStart() {
        client = new MyClient(stage);
        Thread t = new Thread(client);
        t.start();
    }

    /**
     * Description: 游戏结束
     *
     * @param success: Boolean
     * @author: ykn
     * @date: 2022/5/21 13:58
     * @return: void
     */
    public void gameOver(boolean success) {
        client.clear();
        GameOver.load(stage, success);
    }

    /**
     * Description: 打开错误记录
     *
     * @author: ykn
     * @date: 2022/5/21 13:59
     * @return: void
     */
    public void lookWA() {
        LookWAScene.load(stage);
    }
}
