package Server;

import getWords.GetWords;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/**
 * @className: Server
 * @description:
 * @author: ykn
 * @date: 2022/5/17
 **/
public class Server implements Runnable {
    private ServerSocket ss = null;

    public Server() throws IOException {
        ss = new ServerSocket(12345);
        new Thread(this).start();
    }

    List<PairRunnable> lPair = new ArrayList<>();

    public void run() {
        while (true) {
            try {
                Socket s1 = ss.accept();
                Socket s2 = ss.accept();
                PairRunnable pra = new PairRunnable(s1, s2);
                lPair.add(pra);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
