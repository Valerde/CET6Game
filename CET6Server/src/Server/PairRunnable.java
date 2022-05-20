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
    private List<String> words = null;
    private int count;

    public PairRunnable(Socket s1, Socket s2) {

        this.s1 = s1;
        this.s2 = s2;
        try {
            getWords = new GetWords();
            player1 = new Player(s1);
            player2 = new Player(s2);
            player1.otherSide = player2;
            player2.otherSide = player1;
            player1.start();
            player2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        words = getWords.readRandomList();
        count = 0;
    }

    class Player extends Thread {
        PrintStream ps = null;
        BufferedReader br = null;
        int score = 10;
        Player otherSide;
        String msg;
        Boolean sign = false;
        String word;

        public Player(Socket s) throws IOException {
            //String word = getWords.OneWordMsgToTrans();
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        }

        @Override
        public void run() {
            while (true) {

                try {

                    msg = br.readLine();
                    if (msg.equals("ok")) {
                        sign = true;
                    }

                    if (this.sign && this.otherSide.sign) {
                        player1.ps.println("herewego");
                        player2.ps.println("herewego");
                        String word = getWords.OneWordMsgToTrans();
                        player1.ps.println("WORD:" + word + ": ");
                        player2.ps.println("WORD:" + word + ": ");
                        sign = false;
                    }
                    word = getWords.OneWordMsgToTrans();
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
                    this.score -= 1;
                    for (Player p : playerList) {
                        p.ps.println("WORD:" + word + ": ");
                        p.ps.println(ScoreStatus.REDUCT_ONR_POINT + ": :" + p.score + "-" + p.otherSide.score);
                    }
                    break;
                }

                case ScoreStatus.ADD_ONE_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    this.score += 1;
                    for (Player p : playerList) {
                        p.ps.println("WORD:" + word + ": ");
                        p.ps.println(ScoreStatus.ADD_ONE_POINT + ": :" + p.score + "-" + p.otherSide.score);
                    }
                    break;
                }

                case ScoreStatus.REDUCT_TWO_POINT: {
                    String word = getWords.OneWordMsgToTrans();
                    this.score -= 2;
                    for (Player p : playerList) {

                        p.ps.println("WORD:" + word + ": ");
                        p.ps.println(ScoreStatus.REDUCT_TWO_POINT + ": :" + p.score + "-" + p.otherSide.score);

                    }
                    break;
                }
                case ScoreStatus.LOSE: {
                    for (Player p : playerList) {
                        if (p.equals(this)) {
                            this.ps.println(ScoreStatus.LOSE + ": :" + 0);
                        } else {
                            p.ps.println(ScoreStatus.WIN + ": :" + p.score + "-" + p.otherSide.score);
                        }
                    }
                    break;
                }
                case ScoreStatus.KEEP_POINT: {


                    this.score -= 1;


                    this.ps.println("WORD:" + words.get(count) + ": ");
                    count++;
                    this.ps.println(ScoreStatus.KEEP_POINT + ": :" + this.score + "-" + this.otherSide.score);

                    break;
                }

            }//switch
        }
    }


}
