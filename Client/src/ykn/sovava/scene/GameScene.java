package ykn.sovava.scene;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import ykn.sovava.Director;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * description: 游戏界面初始值
 *
 * @className: GameScene
 * @author: ykn
 * @date: 2022/5/18
 **/
public class GameScene {

    Stage stage;
    Label label;
    TextField textField;
    Label labelResult;
    Label labelTranslation;
    Label playerInfo;
    Label scoreLabel;
    Button readyButton;
    MyClient client;

    public void init(Stage stage) {

        //label 用于显示残缺的英文单词
        this.stage = stage;
        label = new Label("incomplete word");
        label.setStyle("-fx-border-width: 3px;-fx-alignment: center;-fx-font-size: 25;-fx-border-radius: 15;-fx-border-color: blue");
        label.setPrefHeight(55);
        label.setPrefWidth(250);
        label.setLayoutY(0);
        label.setLayoutX(50);


        //输入区
        textField = new TextField();
        textField.setPromptText("输入你的答案");
        textField.setLayoutX(350);
        textField.setLayoutY(500);

        labelResult = new Label();
        labelResult.setLayoutX(350);
        labelResult.setLayoutY(300);
        labelResult.setPrefWidth(200);
        labelResult.setPrefHeight(50);
        labelResult.setText("----");
        labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: yellow;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");

        labelTranslation = new Label("这里是翻译");
        labelTranslation.setPrefWidth(Director.WIDTH);
        labelTranslation.setPrefHeight(55);
        labelTranslation.setLayoutY(700);
        labelTranslation.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
        labelTranslation.setLayoutX(-10);

        playerInfo = new Label("己方                    对方");
        playerInfo.setLayoutX(350);
        playerInfo.setLayoutX(350);
        playerInfo.setLayoutY(100);
        playerInfo.setPrefWidth(200);
        playerInfo.setPrefHeight(50);
        playerInfo.setStyle("-fx-border-width: 3px;-fx-border-color: green;-fx-border-radius: 10;-fx-font-size: 18;-fx-alignment: center");

        scoreLabel = new Label(10 + " : " + 10);
        scoreLabel.setLayoutX(350);
        scoreLabel.setLayoutX(400);
        scoreLabel.setLayoutY(100);
        scoreLabel.setPrefWidth(100);
        scoreLabel.setPrefHeight(50);
        scoreLabel.setStyle("-fx-border-radius: 10;-fx-font-size: 18;-fx-alignment: center");


        readyButton = new Button();
        readyButton.setText("ready");
        readyButton.setLayoutY(600);
        readyButton.setLayoutX(350);
        readyButton.setPrefHeight(50);
        readyButton.setPrefWidth(200);
        readyButton.setStyle("-fx-background-color: aqua;-fx-font-size: 24;-fx-alignment:center ");

        AnchorPane root = new AnchorPane(label, textField, labelResult, labelTranslation, playerInfo, scoreLabel, readyButton);
        stage.getScene().setRoot(root);
        stage.show();

        client = new MyClient(label, textField, labelResult, labelTranslation, playerInfo, scoreLabel, readyButton);
        Thread t = new Thread(client);
        t.start();

        //关闭UI线程时同时关闭各子线程
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                client.ps.println("over");
                System.exit(0);
            }
        });

    }

    /**
     * Description: 游戏结束清除界面
     *
     * @author: ykn
     * @date: 2022/5/21 14:13
     * @return: void
     */
    public void clear() {
        client.f = false;
        label = null;
        textField = null;
        labelResult = null;
        labelTranslation = null;
        playerInfo = null;
        scoreLabel = null;
        readyButton = null;
    }
}
