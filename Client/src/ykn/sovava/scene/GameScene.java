package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import ykn.sovava.Director;
import ykn.sovava.Tools.GetIP;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import
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
public class GameScene implements Runnable {
    Socket s = null;
    private PrintStream ps = null;
    private BufferedReader br = null;
    private String nickName = null;

    WordsHandle wordsHandle = null;
    private int score = 10;



    public void init(Stage stage) {

        try {
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            s = new Socket(GetIP.getIP(), 12345);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(this);
        thread.start();
        //label 用于显示残缺的英文单词
        Label label = new Label(wordsHandle.getEnglishIncomplete());
        label.setLayoutY(0);
        label.setLayoutX(200);

        Thread thread1 = new Thread(()->{
            String name = "ykn";
            Platform.runLater(()->{
                int x = 0;
                while (x< Director.HEIGHT){
                    try {
                        Thread.sleep(100);
                        x+=10;
                        label.setLayoutY(x);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
        thread1.start();



        //输入区
        TextField textField = new TextField();
        textField.setLayoutX(350);
        textField.setLayoutY(400);
        textField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String input = textField.getText();
                textField.clear();
            }
        });
        //判断


            AnchorPane root = new AnchorPane(label);
        stage.getScene().setRoot(root);

    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = br.readLine();
                handleMSG(msg);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMSG(String msg){
        String[] strs = msg.split(":");
        switch (strs[0]) {
            case ScoreStatus.ADD_ONE_POINT: {

            }
        }
    }
}
