package ykn.sovava.Tools;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * description: 向文件写入
 * @className: WriteWA
 * @author: ykn
 * @date: 2022/5/20
 **/
public class WriteWA {
    public static void writeLineFile( String content){
        try {
            FileOutputStream out = new FileOutputStream("D:\\yangkainan\\ykn\\JavaLearn\\CET6\\Client\\resource\\text\\WA.txt", true);
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
