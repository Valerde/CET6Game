package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import ykn.sovava.Director;
import ykn.sovava.Tools.GetIP;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;
import ykn.sovava.Tools.WriteWA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * description: 客户端主要逻辑区
 *
 * @className: GameSceneUI
 * @author: ykn
 * @date: 2022/5/19
 **/
public class MyClient implements Runnable {

    public Label label;//TODO 把这个改为Canvas
    public TextField textField;
    public Label labelResult;
    public Label labelTranslation;
    public Label playerInfo;
    public Label scoreLabel;
    public Button readyButton;
    public Socket s;
    public PrintStream ps;
    public BufferedReader br;
    public int myScore;
    public int otherScore;
    public String myAnswer;
    public int Y = 0;
    public TextThread th;
    public static WordsHandle wh;
    public Boolean f = false;

    public MyClient(Label label, TextField textField,
                    Label labelResult, Label labelTranslation, Label playerInfo, Label scoreLabel, Button readyButton) {
        super();
        this.label = label;
        this.textField = textField;
        this.labelResult = labelResult;
        this.labelTranslation = labelTranslation;
        this.playerInfo = playerInfo;
        this.scoreLabel = scoreLabel;
        this.readyButton = readyButton;
        myScore = otherScore = 10;

        try {
            s = new Socket(GetIP.getRealIP(), 12345);
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //准备按钮
        readyButton.setOnAction(event -> {
            ps.println("ok");
            Platform.runLater(() -> {
                readyButton.setText("already……");
            });
        });
        //输入框
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { //判断是否按下回车
                event.consume();
                if (f) {
                    myAnswer = textField.getText();
                    sendMSG(wh, myAnswer);
                }
                textField.clear();

            }
        });
        //监听开始
        try {
            if (br.readLine().equals("herewego")) {
                f = true;
                Platform.runLater(() -> {
                    readyButton.setText("playing……");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (f) {
            getWords();
        }

    }

    /**
     * Description: 获得从服务器发来的消息并判定
     *
     * @author: ykn
     * @date: 2022/5/21 14:18
     * @return: void
     */
    private void getWords() {
        try {
            String msg = br.readLine();
            String[] strs = msg.split(":");
            if (strs[0].equals("WORD")) {
                wh = new WordsHandle(strs[1]);
                Platform.runLater(() -> {
                    set(wh);
                });
                th = new TextThread();
                th.start();
            } else {
                switch (strs[0]) {
                    case ScoreStatus.ADD_ONE_POINT:
                    case ScoreStatus.NO_ANSWER:

                    case ScoreStatus.REDUCT_TWO_POINT: {
                        Platform.runLater(() -> {
                            set(wh, strs[2], strs[0]);
                        });
                        th = new TextThread();
                        th.start();
                        break;
                    }
                    case ScoreStatus.WIN: {
                        set(true, strs[2]);
                        break;
                    }
                    case ScoreStatus.LOSE: {
                        set(false, strs[2]);
                        break;
                    }
                }//switch
            }//if
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void set(WordsHandle wh, String s, String status) {
        if (status.equals(ScoreStatus.NO_ANSWER)) {
            WriteWA.writeLineFile(wh.getEnglish() + " | " + wh.getTranslation() + "\n");
            Platform.runLater(() -> {
                labelResult.setText("Pass");
                labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: yellow;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
            });
            otherScore -= 1;
        } else {
            otherScore = Integer.parseInt(s.split("-")[1] + "0") / 10;
        }
        myScore = Integer.parseInt(s.split("-")[0] + "0") / 10;
        label.setText(wh.getEnglishIncomplete());
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
        label.setLayoutY(0);
        label.setLayoutX(175 - label.getWidth() / 2);

    }

    private void set(WordsHandle wh) {
        label.setText(wh.getEnglishIncomplete());
        labelTranslation.setText(wh.getTranslation());
        scoreLabel.setText(myScore + " : " + otherScore);
        label.setLayoutY(0);
        label.setLayoutX(175 - label.getWidth() / 2);
    }

    private void set(Boolean flag, String s) {
        Director.getInstance().gameOver(flag);
    }

    private int time = 100;

    /**
     * Description: 单词掉落子线程
     *
     * @author: ykn
     * @date: 2022/5/21 14:17
     */
    public class TextThread extends Thread {
        Boolean RUN = true;

        public void run() {
            Y = 0;
            time += 100;
            while (RUN) {
                try {
                    Thread.sleep(time);
                    Y += 5;
                    Platform.runLater(() -> {
                        label.setLayoutY(Y);
                    });
                    if (Y >= 700) {
                        ps.println(ScoreStatus.NO_ANSWER);
                    }
                } catch (Exception ex) {
                }
            }
        }
    }

    /**
     * Description: 每次反馈服务器
     *
     * @param wh:     WordHandle
     * @param answer: 玩家输入的答案
     * @author: ykn
     * @date: 2022/5/21 14:16
     * @return: void
     */
    public void sendMSG(WordsHandle wh, String answer) {
        if (wh.getEnglish().equals(answer)) {
            ps.println(ScoreStatus.ADD_ONE_POINT);
            Platform.runLater(() -> {
                labelResult.setText("Right");
                labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: green;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
            });
        } else {
            ps.println(ScoreStatus.REDUCT_TWO_POINT);
            WriteWA.writeLineFile(wh.getEnglish() + " | " + wh.getTranslation() + "\n");
            Platform.runLater(() -> {
                labelResult.setText("Wrong");
                labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
            });
        }
    }

}
