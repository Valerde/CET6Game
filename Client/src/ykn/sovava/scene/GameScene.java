package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    //TODO 把空间写到这里或者放弃登录或者让登录控件消失
    Socket s = null;
    private PrintStream ps = null;
    private BufferedReader br = null;
    private String nickName = null;


    WordsHandle wordsHandle = new WordsHandle();
    private int score = 10;


    public void init(Stage stage) {




        //label 用于显示残缺的英文单词
        //Label label = new Label(wordsHandle.getEnglishIncomplete());
        this.stage = stage;
        label = new Label("f__e");
        label.setStyle("-fx-border-width: 3px;-fx-alignment: center;-fx-font-size: 35;-fx-border-radius: 15;-fx-border-color: blue");
        label.setPrefHeight(55);
        label.setMaxWidth(200);
        label.setMinWidth(150);
        label.setLayoutY(0);
        label.setLayoutX(100);


        //输入区
        textField = new TextField();
        textField.setPromptText("uurunidedaan");
        textField.setLayoutX(350);
        textField.setLayoutY(500);

//        textField.setOnAction(e -> {
//            Thread thread = new Thread(() -> {
//
//                Platform.runLater(() -> {
//                    input = textField.getText();
//                    textField.clear();
//                });
//
//            });
//            System.out.println(input);
//            thread.start();
//
//        });

        labelResult = new Label("right");
        labelResult.setLayoutX(350);
        labelResult.setLayoutY(300);
        labelResult.setPrefWidth(200);
        labelResult.setPrefHeight(50);
        labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");


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


        AnchorPane root = new AnchorPane(label, textField, labelResult, labelTranslation,playerInfo,scoreLabel);
        stage.getScene().setRoot(root);
        stage.show();
        new Thread(new MyClient(stage,label,textField,labelResult,labelTranslation,playerInfo,scoreLabel)).start();
//        Thread thread = new Thread(this);
//        thread.start();
//
//        Thread thread1 = new Thread(() -> {
//            String name = "ykn";
//            Platform.runLater(() -> {
//                int x = 0;
//                while (x < Director.HEIGHT) {
//                    try {
//                        Thread.sleep(100);
//                        x += 10;
//                        label.setLayoutY(x);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        });
//        thread1.start();

    }

//    @Override
//    public void run() {
//        while (true) {
////            try {
//////                String msg = br.readLine();
//////                handleMSG(msg);
////
////
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
//        }
//    }

    public void handleMSG(String msg) {
        String[] strs = msg.split(":");
        switch (strs[0]) {
            case ScoreStatus.ADD_ONE_POINT: {

            }
        }
    }
}
