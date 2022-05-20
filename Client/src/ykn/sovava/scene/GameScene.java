package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import ykn.sovava.Director;
import ykn.sovava.Tools.GetIP;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @className: GameScene
 * @description:
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

    public void init(Stage stage) {

        //label 用于显示残缺的英文单词
        this.stage = stage;
        label = new Label("f__e");
        label.setStyle("-fx-border-width: 3px;-fx-alignment: center;-fx-font-size: 25;-fx-border-radius: 15;-fx-border-color: blue");
        label.setPrefHeight(55);
//        label.setMaxWidth(300);
//        label.setMinWidth(150);
        label.setPrefWidth(250);
        label.setLayoutY(0);
        label.setLayoutX(175-label.getWidth()/2);


        //输入区
        textField = new TextField();
        textField.setPromptText("输入你的答案");
        textField.setLayoutX(350);
        textField.setLayoutY(500);


        labelResult = new Label("right");
        labelResult.setLayoutX(350);
        labelResult.setLayoutY(300);
        labelResult.setPrefWidth(200);
        labelResult.setPrefHeight(50);
        labelResult.setText("----");
        labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: yellow;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");

        //labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");


        labelTranslation = new Label("CF站怪帮教亏kg基层抗不");
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

        scoreLabel = new Label("13 : 4");
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
        new Thread(new MyClient(stage, label, textField, labelResult, labelTranslation, playerInfo, scoreLabel,readyButton)).start();

    }

}
