package Client;

import ykn.sovava.Tools.GetIP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;

import ykn.sovava.Tools.ScoreStatus;
import javafx.stage.Stage;

/**
 * @className: CetClient
 * @description:
 * @author: ykn
 * @date: 2022/5/17
 **/
public class CETClient implements Runnable {
    Socket s = null;
    private PrintStream ps = null;
    private BufferedReader br = null;
    private String nickName = null;

    Map<String, String> words = null;
    private int score = 10;

    private Stage stage = null;

    public CETClient(Stage stage) {
        try {
            this.stage = stage;
            s = new Socket(GetIP.getRealIP(), 12345);
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            new Thread(this).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = br.readLine();
                String[] strs = msg.split(":");
                switch (strs[0]) {
                    case ScoreStatus.ADD_ONE_POINT: {

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
