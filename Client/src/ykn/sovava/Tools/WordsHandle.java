package ykn.sovava.Tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: WordsHandle
 * @description:
 * @author: ykn
 * @date: 2022/5/17
 **/
public class WordsHandle {
    /**
     * @description: 单词组合,先翻译后单词
     */
    private String english = null;
    private String translation = null;
    private String englishIncomplete = null;

    /**
     * @description: 传过来单词与翻译
     * @author: ykn
     * @time: 2022/5/17 14:12
     */
    public WordsHandle(String msg) {
        String[] words = msg.split("|");
        english = words[0];
        translation = words[1];
        englishIncomplete = words[2];
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public void setEnglishIncomplete(String englishIncomplete) {
        this.englishIncomplete = englishIncomplete;
    }

    public String getEnglish() {
        return english;
    }

    public String getTranslation() {
        return translation;
    }

    public String getEnglishIncomplete() {
        return englishIncomplete;
    }
}
