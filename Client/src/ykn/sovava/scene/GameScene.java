package ykn.sovava.scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
public abstract class GameScene {

    public Stage stage;
    public TextField textField;
    public Label labelResult;
    public Label labelTranslation;
    public Label playerInfo;
    public Label scoreLabel;
    public Button readyButton;
    public Canvas canvas;
    public GraphicsContext graphicsContext;

    GameScene(Stage stage) {
        this.stage = stage;
        //掉落区
        canvas = new Canvas(350, 700);
        graphicsContext = canvas.getGraphicsContext2D();
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        //输入区
        textField = new TextField();
        textField.setPromptText("输入你的答案");
        textField.setLayoutX(350);
        textField.setLayoutY(500);
        //正确与否提示区
        labelResult = new Label();
        labelResult.setLayoutX(350);
        labelResult.setLayoutY(300);
        labelResult.setPrefWidth(200);
        labelResult.setPrefHeight(50);
        labelResult.setText("----");
        labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: yellow;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
        //底部翻译区
        labelTranslation = new Label("这里是翻译");
        labelTranslation.setPrefWidth(Director.WIDTH);
        labelTranslation.setPrefHeight(55);
        labelTranslation.setLayoutY(700);
        labelTranslation.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
        labelTranslation.setLayoutX(-10);
        //双方显示区
        playerInfo = new Label("己方                    对方");
        playerInfo.setLayoutX(350);
        playerInfo.setLayoutX(350);
        playerInfo.setLayoutY(100);
        playerInfo.setPrefWidth(200);
        playerInfo.setPrefHeight(50);
        playerInfo.setStyle("-fx-border-width: 3px;-fx-border-color: green;-fx-border-radius: 10;-fx-font-size: 18;-fx-alignment: center");
        //比分显示区
        scoreLabel = new Label(10 + " : " + 10);
        scoreLabel.setLayoutX(350);
        scoreLabel.setLayoutX(400);
        scoreLabel.setLayoutY(100);
        scoreLabel.setPrefWidth(100);
        scoreLabel.setPrefHeight(50);
        scoreLabel.setStyle("-fx-border-radius: 10;-fx-font-size: 18;-fx-alignment: center");
        //开始按钮
        readyButton = new Button();
        readyButton.setText("ready");
        readyButton.setLayoutY(600);
        readyButton.setLayoutX(350);
        readyButton.setPrefHeight(50);
        readyButton.setPrefWidth(200);
        readyButton.setStyle("-fx-background-color: aqua;-fx-font-size: 24;-fx-alignment:center ");
        //锚点布局
        AnchorPane root = new AnchorPane(textField, labelResult, labelTranslation, playerInfo, scoreLabel, readyButton, canvas);
        stage.getScene().setRoot(root);
        stage.show();
    }

}
