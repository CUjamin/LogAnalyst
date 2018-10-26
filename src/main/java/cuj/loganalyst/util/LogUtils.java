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
}
