package getWords;

import Tools.GetRandom;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @className: GetWords
 * @description:
 * @author: ykn
 * @date: 2022/5/4
 **/
public class GetWords {

    private static int collectionLen = 20;

    private static final int total = 1286;

    /**
     * @description: 一组不重合的随机数
     */
    private List<Integer> listRandom = new ArrayList<>();

    /**
     * @description: 所有的单词。
     */
    private static Map<Integer, String> wordTotalByOrder = null;

    public GetWords() throws Exception {
        listRandom = GetRandom.getArrayListRandom(total, collectionLen);
        wordTotalByOrder = readIn();
    }

    /**
     * description: 获取一次游戏的单词数
     * @return: int
     * @author: ykn
     * @time: 2022/5/17 13:51
     */
    public int getCollectionLen() {
        return collectionLen;
    }

    /**
     * description: 设置一次游戏的单词数
     * @param: collectionLen
     * @author: ykn
     * @time: 2022/5/17 13:51
     */
    public void setCollectionLen(int collectionLen) {
        GetWords.collectionLen = collectionLen;
    }

    /**
     * description: 从文件中读取单词
     * @return: java.util.Map<java.lang.Integer, java.lang.String>
     * @author: ykn
     * @time: 2022/5/17 13:50
     */
    private Map<Integer, String> readIn() throws Exception {
        File file = new File("CET6Server\\Words.txt");
        Map<Integer, String> words = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String s = null;
        int i = 0;
        while ((s = br.readLine()) != null) {
            words.put(i, s);
            i++;
        }
        br.close();
        return words;
    }

    /**
     * Description: 随机获取一组单词
     * @author: ykn
     * @date: 2022/5/21 14:28
     * @return: java.util.List<java.lang.String>
     */
    public List<String> readRandomList() {

        List<String> wordCollection = new ArrayList<>();
        String s;
        for (int i = 0; i < collectionLen; i++) {
            s=OneWordMsgToTrans();
            wordCollection.add(s);
            wordCollection.add(s);
        }
        return wordCollection;
    }

    /**
     * Description: 随机获取一个要发送的Word组合,
     * 格式为english + "|-" + translation + "|" + englishIncomplete;
     * @author: ykn
     * @date: 2022/5/21 14:29
     * @return: java.lang.String
     */
    public String OneWordMsgToTrans() {
        int index = GetRandom.getRandom(total);
        String msg = wordTotalByOrder.get(index);
        String[] word = msg.split(" -");
        String translation = null, english = null;
        translation = word[1];
        english = word[0];
        int length = english.length();
        int i1 = GetRandom.getRandom(length);
        int i2 = -1;

        do {
            i2 = GetRandom.getRandom(length);
        } while (i1 == i2);

        StringBuilder handledEnglish = new StringBuilder("");
        for (int i = 0; i < english.length(); i++) {
            if (i == i1 || i == i2) {
                handledEnglish.append(english.charAt(i));
            } else {
                handledEnglish.append("_");
            }
        }

        String englishIncomplete = handledEnglish.toString();
        return english + "|-" + translation + "|" + englishIncomplete;
    }
}
