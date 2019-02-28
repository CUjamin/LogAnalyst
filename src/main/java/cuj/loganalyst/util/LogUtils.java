package cuj.loganalyst.util;

import java.util.regex.Pattern;

/**
 * @Auther: cujamin
 * @Date: 2018/10/25 16:39
 * @Description:
 */
public class LogUtils {
    public static final String AND="&&";
    public static final String OR="\\|\\|";

    public static boolean containWord(final String data,final String word)
    {
        String pattern = String.format(".*%s.*", word);
        return Pattern.matches(pattern, data);
    }

    public static boolean containWordOR(final String data,final String word)
    {
        boolean isContain = false;
        String[] orWords = word.split(OR);
        for (String orWord : orWords){
            isContain = containWordAND(data,orWord);
            if(isContain){
                break;
            }
        }
        return isContain;
    }

    public static boolean containWordAND(final String data,final String word)
    {
        boolean isContain = true;
        String[] andWords = word.split(AND);
        for(String andWord: andWords){
            isContain =  containWord(data,andWord);
            if(!isContain){
                break;
            }
        }
        return isContain;
    }

    private static String getPatternOrder(final String[] words){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(".*");
        for(int i=0;i<words.length;++i){
            stringBuffer.append(words[i]);
            stringBuffer.append(".*");
        }
        return stringBuffer.toString();
    }

    public static String getPatternOrder(final String word){
        String[] containWords = word.split(",");
        return getPatternOrder(containWords);
    }

    public static boolean containWordOrder(String data,String pattern) {
        return containWord(data,pattern);
    }

    public static boolean containWordDisorder(String data,String[] words) {
        boolean contain = false;
        for(int i=0;i<words.length;++i){
            contain = containWord(data,words[i]);
            if(!contain){
                break;
            }
        }
        return contain;
    }
}
