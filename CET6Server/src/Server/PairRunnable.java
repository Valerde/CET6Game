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
public class PairRunnable implements Runnable {
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
            player1 = new Player(s1);
            player2 = new Player(s2);
            player1.start();
            player2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
    }

    @Override
    public void run() {
        while (true) {
            String word = getWords.OneWordMsgToTrans();

            player1.ps.println(word);
            player2.ps.println(word);
        }

    }

    class Player extends Thread {
        PrintStream ps = null;
        BufferedReader br = null;

        public Player(Socket s) throws IOException {
            String word = getWords.OneWordMsgToTrans();
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));

        }


        @Override
        public void run() {
            while (true) {
                try {
                    String msg = br.readLine();
                    switch (msg) {

                        case ScoreStatus.REDUCT_ONR_POINT: {
                            String word = getWords.OneWordMsgToTrans();
                            for (Player p : playerList) {
                                p.ps.println("WORD: " + word);
                                p.ps.println(ScoreStatus.REDUCT_ONR_POINT + ": ");
                            }
                            break;
                        }

                        case ScoreStatus.ADD_ONE_POINT: {
                            String word = getWords.OneWordMsgToTrans();
                            for (Player p : playerList) {
                                p.ps.println("WORD: " + word);
                                if (p.equals(this)) {
                                    this.ps.println(ScoreStatus.ADD_ONE_POINT + ": ");
                                } else {
                                    p.ps.println(ScoreStatus.KEEP_POINT);
                                }
                            }
                            break;
                        }

                        case ScoreStatus.REDUCT_TWO_POINT: {
                            String word = getWords.OneWordMsgToTrans();
                            for (Player p : playerList) {
                                p.ps.println("WORD: " + word);
                                if (p.equals(this)) {
                                    this.ps.println(ScoreStatus.REDUCT_TWO_POINT);
                                } else {
                                    p.ps.println(ScoreStatus.KEEP_POINT + ": ");
                                }
                            }
                            break;
                        }
                        case ScoreStatus.LOSE: {
                            for (Player p : playerList) {
                                if (p.equals(this)) {
                                    this.ps.println(ScoreStatus.LOSE + ": ");
                                } else {
                                    p.ps.println(ScoreStatus.WIN + ": ");
                                }
                            }
                            break;
                        }


                    }//switch

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }
}
