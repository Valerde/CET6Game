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
 * description: 一个对局是一个线程, 在线程中继续管理两个玩家线程
 *
 * @className: PairRunnable
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
        Boolean run = true;

        /**
         * Description: 内部类,每个对象为一个玩家,一个对局内只有两个玩家。
         *
         * @author: ykn
         * @date: 2022/5/21 15:11
         */
        public Player(Socket s) throws IOException {
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }

        @Override
        public void run() {
            while (run) {
                try {
                    msg = br.readLine()+"";
                    if (msg.equals("ok")) {
                        sign = true;
                    } else if (msg.equals("over")) {
                        run = false;
                        this.otherSide.run = false;
                        continue;
                    }

                    if (this.sign && this.otherSide.sign) {
                        player1.ps.println("herewego");
                        player2.ps.println("herewego");
                        String word = getWords.OneWordMsgToTrans();
                        player1.ps.println("WORD:" + word + ": ");
                        player2.ps.println("WORD:" + word + ": ");
                        this.sign = false;
                        this.otherSide.sign = false;
                    }
                    word = getWords.OneWordMsgToTrans();
                    judge(msg);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        /**
         * Description: 对客户端发来的消息进行判断
         *
         * @param msg: 客户端传过来的消息
         * @author: ykn
         * @date: 2022/5/21 14:25
         * @return: void
         */
        public void judge(String msg) throws InterruptedException {
            switch (msg) {
                case ScoreStatus.ADD_ONE_POINT: {
                    this.score += 1;
                    this.ps.println("WORD:" + word + ": ");
                    this.otherSide.ps.println("WORD:" + word + ": ");
                    this.ps.println(ScoreStatus.ADD_ONE_POINT + ": :" + this.score + "-" + this.otherSide.score);
                    this.otherSide.ps.println(ScoreStatus.ADD_ONE_POINT + ": :" + this.otherSide.score + "-" + this.score);
                    break;
                }

                case ScoreStatus.REDUCT_TWO_POINT: {
                    this.score -= 2;
                    if (judgeIfLose()) break;
                    this.ps.println("WORD:" + word + ": ");
                    this.otherSide.ps.println("WORD:" + word + ": ");
                    this.ps.println(ScoreStatus.REDUCT_TWO_POINT + ": :" + this.score + "-" + this.otherSide.score);
                    this.otherSide.ps.println(ScoreStatus.REDUCT_TWO_POINT + ": :" + this.otherSide.score + "-" + this.score);
                    break;
                }
                case ScoreStatus.LOSE: {
                    break;
                }
                case ScoreStatus.NO_ANSWER: {
                    this.score -= 1;
                    if (judgeIfLose()) break;
                    this.ps.println("WORD:" + words.get(count) + ": ");
                    count++;
                    this.ps.println(ScoreStatus.NO_ANSWER + ": :" + this.score + "-" + this.otherSide.score);
                    break;
                }

            }//switch
        }

        /**
         * Description: 每次扣分后，判断该玩家是否已经输了，当所有玩家都是0分一下时，均失败。
         *
         * @author: ykn
         * @date: 2022/5/21 15:12
         * @return: java.lang.Boolean
         */
        private Boolean judgeIfLose() throws InterruptedException {
            if (this.score <= 0) {
                this.ps.println(ScoreStatus.LOSE + ": :" + "0" + "-" + this.otherSide.score);
                Thread.sleep(100);
                this.otherSide.ps.println(ScoreStatus.WIN + ": :" + this.otherSide.score + "-" + this.score);
                return true;
            }
            return false;
        }
    }
}
