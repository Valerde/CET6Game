package ykn.sovava.Tools;

/**
 * description: 处理单词组
 * @className: WordsHandle
 * @author: ykn
 * @date: 2022/5/17
 **/
public class WordsHandle {

    private String english = null;
    private String translation = null;
    private String englishIncomplete = null;

    /**
     * Description: 构造函数,
     * @author: ykn
     * @date: 2022/5/22 11:46
     * @param msg:msg格式为  english|translation|englishIncomplete
     */
    public WordsHandle(String msg) {
        String[] words = msg.split("\\|");
        english = words[0];
        translation = words[1];
        englishIncomplete = words[2];
        System.out.println(english+"*"+translation+"*"+englishIncomplete);
    }

    public WordsHandle() {
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
