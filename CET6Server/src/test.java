import getWords.GetWords;

import java.util.Map;

/**
 * @className: test
 * @description:
 * @author: ykn
 * @date: 2022/5/4
 **/
public class test {
    static class testThread extends Thread {
        int i = 0;

        @Override
        public void run() {
            while (true) {
                System.out.println(i);
                i++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        GetWords getWords = new GetWords();

        //new testThread().start();
        //while (true)
        System.out.println(getWords.OneWordMsgToTrans());
        String s = "1";
        switch(s){
            case "2":{
                System.out.println("haha");
                break;
            }
            case "1":{
                System.out.println("kj?");
                break;
            }


        }
    }
}
