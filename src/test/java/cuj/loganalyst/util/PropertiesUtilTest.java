package cuj.loganalyst.util;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @Auther: cujamin
 * @Date: 2018/10/26 17:27
 * @Description:
 */
public class PropertiesUtilTest {
    @Before
    public void before(){
        try{
            PropertiesUtil.initPropertiesFile(ConfigKey.CONFIG_FILE_NAME);
        }catch (IOException ioe){
            System.out.println("IOException");
        }
    }

    @Test
    public void getString() throws Exception{
        System.out.println("--"+PropertiesUtil.getString(ConfigKey.PATH, ConfigKey.PATH_DEFAULT));
    }

    @Test
    public void getInteger() throws Exception{
        System.out.println("--"+PropertiesUtil.getInteger("xxx", TaskType.SPLIT_LOG));
    }
}