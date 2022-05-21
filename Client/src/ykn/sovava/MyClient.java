package ykn.sovava;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ykn.sovava.Tools.GetIP;
import ykn.sovava.Tools.ScoreStatus;
import ykn.sovava.Tools.WordsHandle;
import ykn.sovava.Tools.WriteWA;
import ykn.sovava.scene.GameSceneChange;
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
public class MyClient extends GameSceneChange implements Runnable {
    public Socket s;
    public PrintStream ps;
    public BufferedReader br;
    public String myAnswer;
    public static WordsHandle wh;
    public Boolean f = false;
    private Refresh refresh;
    private boolean running = true;

    public MyClient(Stage stage) {
        super(stage);
        refresh = new Refresh();
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
                if (textField != null) textField.clear();
            }
        });
        //监听服务器发来的开始信号
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

        //关闭UI线程时同时关闭各子线程
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                ps.println("over");
                System.exit(0);
            }
        });

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
                Y = 20;
                running = true;
                refresh.start();
            } else {
                switch (strs[0]) {
                    case ScoreStatus.ADD_ONE_POINT:
                    case ScoreStatus.NO_ANSWER:
                    case ScoreStatus.REDUCT_TWO_POINT: {
                        Platform.runLater(() -> {
                            set(wh, strs[2], strs[0]);
                        });
                        Y = 20;
                        running = true;
                        refresh.start();
                        break;
                    }
                    case ScoreStatus.WIN: {
                        set(true);
                        break;
                    }
                    case ScoreStatus.LOSE: {
                        set(false);
                        break;
                    }
                }//switch
            }//if
        } catch (IOException e) {
            e.printStackTrace();
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
                if (labelResult != null) {
                    labelResult.setText("Right");
                    labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: green;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
                }
            });
        } else {
            ps.println(ScoreStatus.REDUCT_TWO_POINT);
            WriteWA.writeLineFile(wh.getEnglish() + " | " + wh.getTranslation() + "\n");
            Platform.runLater(() -> {
                if (labelResult != null) {
                    labelResult.setText("Wrong");
                    labelResult.setStyle("-fx-border-width: 3px;-fx-border-color: red;-fx-border-radius: 10;-fx-font-size: 30;-fx-alignment: center");
                }
            });
        }
    }

    /**
     * Description: 游戏结束清除界面
     *
     * @author: ykn
     * @date: 2022/5/21 14:13
     * @return: void
     */
    public void clear() {
        f = false;
        textField = null;
        labelResult = null;
        labelTranslation = null;
        playerInfo = null;
        scoreLabel = null;
        readyButton = null;
        canvas = null;
    }

    /**
     * 刷新,Animation创建一个计时器,将在每一帧被调用
     */
    private class Refresh extends AnimationTimer {
        @Override
        public void handle(long now) {
            if (running) {
                paint(wh.getEnglishIncomplete());
                if (Y >= 700) {
                    ps.println(ScoreStatus.NO_ANSWER);
                    running = false;
                    this.stop();
                }
            }

        }
    }


}
