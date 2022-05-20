package Server;

import Tools.ScoreStatus;
import getWords.GetWords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: PairRunnable
 * @description: 一个对局是一个线程, 在线程中继续管理两个线程
 * @author: ykn
 * @date: 2022/5/17
 **/
public class PairRunnable {
    private Socket s1 = null;
    private Socket s2 = null;
    public GetWords getWords = null;
    private Player player1 = null;
    private Player player2 = null;
    private List<Player> playerList = null;

    public PairRunnable(Socket s1, Socket s2) {

        this.s1 = s1;
        this.s2 = s2;
        try {
            getWords = new GetWords();
            player1 = new Player(s1);
            player2 = new Player(s2);
            player1.start();
            player2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        String word = getWords.OneWordMsgToTrans();

        player1.ps.println("WORD:" + word + ": ");
        player2.ps.println("WORD:" + word + ": ");
    }

//    @Override
//    public void run() {
//
//    }

    class Player extends Thread {
        PrintStream ps = null;
        BufferedReader br = null;
        int score = 10;

        public Player(Socket s) throws IOException {
            //String word = getWords.OneWordMsgToTrans();
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        }


        @Override
        public void run() {
            while (true) {
                try {
                    String msg = br.readLine();
                    judge(msg);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        public void judge(String msg) {
            switch (msg) {

                case ScoreStatus.REDUCT_ONR_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    score -= 2;
                    for (Player p : playerList) {
                        p.ps.println("WORD:" + word + ": ");
                        p.ps.println(ScoreStatus.REDUCT_ONR_POINT + ": :" + p.score);
                    }
                    break;
                }

                case ScoreStatus.ADD_ONE_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    score += 1;
                    for (Player p : playerList) {
                        p.ps.println("WORD:" + word + ": ");
                        if (p.equals(this)) {
                            this.ps.println(ScoreStatus.ADD_ONE_POINT + ": :" + p.score);
                        } else {
                            p.ps.println(ScoreStatus.KEEP_POINT + ": :" + p.score);
                        }
                    }
                    break;
                }

                case ScoreStatus.REDUCT_TWO_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    for (Player p : playerList) {

                        p.ps.println("WORD:" + word + ": ");
                        if (p.equals(this)) {
                            p.score -= 2;
                            this.ps.println(ScoreStatus.REDUCT_TWO_POINT + ": :" + p.score);
                        } else {
                            //TODO  score计算错了,应该发自己的分数和对手的分数,或者不发自己的,只发对手的
                            p.ps.println(ScoreStatus.KEEP_POINT + ": :" + p.score);
                        }
                    }
                    break;
                }
                case ScoreStatus.LOSE: {
                    for (Player p : playerList) {
                        if (p.equals(this)) {
                            this.ps.println(ScoreStatus.LOSE + ": :" + 0);
                        } else {
                            p.ps.println(ScoreStatus.WIN + ": :" + p.score);
                        }
                    }
                    break;
                }
                case ScoreStatus.KEEP_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    for (Player p : playerList) {
                        p.ps.println("WORD:" + word + ": ");
                        p.ps.println(ScoreStatus.KEEP_POINT + ": :" + p.score);
                    }
                    break;
                }

            }//switch
        }
    }


}
