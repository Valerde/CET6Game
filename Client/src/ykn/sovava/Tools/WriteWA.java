package ykn.sovava.Tools;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @className: WriteWA
 * @description:
 * @author: ykn
 * @date: 2022/5/20
 **/
public class WriteWA {
    public static void writeLineFile( String content){
        try {
            FileOutputStream out = new FileOutputStream("haha.txt", true);
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
