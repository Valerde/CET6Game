package ykn.sovava.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import ykn.sovava.Director;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: LookWA
 * @description:
 * @author: ykn
 * @date: 2022/5/21
 **/
public class LookWAController {
    @FXML
    private TextArea area;

    @FXML
    private Button ret;

    @FXML
    void clickToBack(ActionEvent event) {
        Director.getInstance().gameStart();
    }

    public void initialize(){
        //List<String> wordsWA = readIn();
        area.setFont(new Font(20));
        area.setText(readIn());
    }

    /**
     * Description: 读入错误过的单词
     * @author: ykn
     * @date: 2022/5/21 14:21
     * @return: java.lang.String
     */
    private String readIn()  {
        File file = new File("D:\\yangkainan\\ykn\\JavaLearn\\CET6\\Client\\resource\\text\\WA.txt");
        List<String> words = new ArrayList<>();
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader(file));
            String s = null;

            while ((s = br.readLine()) != null) {
                words.add(s);
                sb.append(s).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
