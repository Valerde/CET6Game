package ykn.sovava.scene;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import ykn.sovava.Tools.GetIP;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @className: GameSceneUI
 * @description:
 * @author: ykn
 * @date: 2022/5/19
 **/
public class MyClient implements Runnable {

    public Stage stage;
    public Label label;//TODO 把这个改为Canvas
    public TextField textField;
    public Label labelResult;
    public Label labelTranslation;
    public Label playerInfo;
    public Label scoreLabel;
    public Socket s;
    public PrintStream ps;
    public BufferedReader br;
    public int score;
    public String myAnswer;
    public int Y = 0;
    public TextThread th;
    public static WordsHandle wh;
    private String english = null;
    private String translation = null;
    private String englishIncomplete = null;


    public MyClient(Stage stage, Label label, TextField textField,
                    Label labelResult, Label labelTranslation, Label playerInfo, Label scoreLabel) {
        super();
        this.stage = stage;
        this.label = label;
        this.textField = textField;
        this.labelResult = labelResult;
        this.labelTranslation = labelTranslation;
        this.playerInfo = playerInfo;
        this.scoreLabel = scoreLabel;
        score = 10;
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
        set();
        th = new TextThread();
        th.start();
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) { //判断是否按下回车
                event.consume();
                myAnswer = textField.getText();
                textField.clear();
                sendMSG(wh, myAnswer);
            }
        });

        while (true) {
            getWords();
        }

    }

    private void getWords() {
        //Platform.runLater(() -> {
        String msg = null;
        try {
            msg = br.readLine();
            String[] strs = msg.split(":");
            if (strs[0].equals("WORD")) {
                wh = new WordsHandle(strs[1]);
                String[] words = strs[1].split("\\|");
                english = words[0];
                translation = words[1];
                englishIncomplete = words[2];
            } else {
                switch (strs[0]) {
                    case ScoreStatus.ADD_ONE_POINT:
                    case ScoreStatus.KEEP_POINT:
                    case ScoreStatus.REDUCT_ONR_POINT:
                    case ScoreStatus.REDUCT_TWO_POINT: {
                        Platform.runLater(() -> {
                            set(wh, strs[2]);
                        });

                        score = Integer.parseInt(strs[2]);
                        sendLose(strs[0]);
                        th = new TextThread();
                        th.start();
//                        new Thread(() -> {
//
//                            Platform.runLater(() -> {
//                                Boolean RUN = true;
//                                Y = 0;
//                                while (RUN) {
//                                    try {
//                                        Thread.sleep(100);
//                                        Y += 5;
//                                        label.setLayoutY(Y);
//                                        if (Y == 700) {
//                                            ps.println(ScoreStatus.KEEP_POINT);
//                                            RUN=false;
//                                        }
//                                    } catch (Exception ex) {
//                                    }
//                                }
//                            });
//                        }).start();
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

        //});
    }


    private void set(WordsHandle wh, String s) {
        label.setText(englishIncomplete);
        labelTranslation.setText(translation);
        scoreLabel.setText(score + " : " + s);
        label.setLayoutY(0);
        label.setLayoutX(100);

    }

    private void set(Boolean flag, String s) {

    }

    public void set() {
        label.setText("___");
        labelTranslation.setText("中南大学");
        scoreLabel.setText(10 + " : " + 10);
        label.setLayoutY(0);
        label.setLayoutX(100);
    }
    private int time = 100;
//    public class TextRunnable implements Runnable{
//
//        @Override
//        public void run() {
//
//        }
//    }

    public class TextThread extends Thread {
        Boolean RUN = true;

        public void run() {
            Y = 0;
            time +=100;
            while (RUN) {
                try {
                    Thread.sleep(time);
                    Y += 5;
                    //System.out.println(Y);
                    Platform.runLater(()->{
                        label.setLayoutY(Y);
                    });

                    if (Y >= 700) {
                        ps.println(ScoreStatus.KEEP_POINT);
                        //RUN = false;
                        //
                    }
                } catch (Exception ex) {
                }
            }

        }
    }

    public void sendMSG(WordsHandle wh, String answer) {
        if (wh.getEnglish().equals(answer)) {
            ps.println(ScoreStatus.ADD_ONE_POINT);
            score += 1;
        } else {
            ps.println(ScoreStatus.REDUCT_TWO_POINT);
        }
    }

    public void sendLose(String s) {
        if (s.equals(ScoreStatus.REDUCT_ONR_POINT) || s.equals(ScoreStatus.REDUCT_TWO_POINT)) {
            if (score <= 0) {
                ps.println(ScoreStatus.LOSE);
            }
        }

    }
}
