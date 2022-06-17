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
    public WriteWA() {
    }

    public void writeLineFile(String content){
        try {
            FileOutputStream out = new FileOutputStream(this.getClass().getResource("/text/WA.txt").getPath(), true);
            out.write(content.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
