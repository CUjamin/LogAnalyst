package cuj.loganalyst.util;

import java.util.regex.Pattern;

/**
 * @Auther: cujamin
 * @Date: 2018/10/25 16:39
 * @Description:
 */
public class LogUtils {

    public static boolean containWord(String data,String word)
    {
        String pattern = String.format(".*%s.*", word);
        return Pattern.matches(pattern, data);
    }
    public static String getPatternOrder(String[] words){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(".*");
        for(int i=0;i<words.length;++i){
            stringBuffer.append(words[i]);
            stringBuffer.append(".*");
        }
        return stringBuffer.toString();
    }

    public static String getPatternOrder(String word){
        String[] containWords = word.split(",");
        return getPatternOrder(containWords);
    }

    public static boolean containWordOrder(String data,String pattern) {
        return Pattern.matches(pattern, data);
    }

    public static boolean containWordDisorder(String data,String[] words) {
        boolean contain = false;
        for(int i=0;i<words.length;++i){
            contain = Pattern.matches(words[i], data);
            if(!contain){
                break;
            }
        }
        return contain;
    }
}
