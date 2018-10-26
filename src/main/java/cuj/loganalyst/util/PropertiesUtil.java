package cuj.loganalyst.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Auther: cujamin
 * @Date: 2018/10/26 17:16
 * @Description:
 */
public class PropertiesUtil {
    private static Properties properties;
    public static void initPropertiesFile(String fileName) throws IOException{
        properties = new Properties();
        properties.load(
                new InputStreamReader(
                        new FileInputStream(fileName),"UTF-8"));
    }

    public static String getString(String key,String defaultValue)throws Exception{
        return properties.getProperty(key,defaultValue);
    }

    public static Integer getInteger(String key,int defaultValue)throws Exception{
        return Integer.valueOf( properties.getProperty(key,Integer.toString(defaultValue)));
    }
}
