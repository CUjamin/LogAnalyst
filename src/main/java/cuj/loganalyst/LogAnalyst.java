package cuj.loganalyst;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.LogAnalysisService;
import cuj.loganalyst.service.LogAnalysisServiceImpl;
import cuj.loganalyst.util.PropertiesUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalyst {
    private final static Logger log = LoggerFactory.getLogger(LogAnalyst.class);
    private static LogAnalysisService logAnalysisService =  new LogAnalysisServiceImpl();
    private static int type ;
    private static String path;
    private static String charsetName;
    private static String containWord;
    private static String toFileName;
    private static File[] files = null;
    private static Map<String,Object> param = new HashMap<String,Object>();

    public static void main(String[] args) {

        initParam();
        logAnalysisService.handle(path,toFileName,charsetName,containWord,param);

    }

    private static void initParam() {
        PropertyConfigurator.configure(LogAnalyst.class.getClassLoader().getResource(ConfigKey.LOG_CONFIG_FILE_NAME));
        try {
            PropertiesUtil.initPropertiesFile(ConfigKey.CONFIG_FILE_NAME);
        } catch (IOException e) {
            log.info("读取配置文件出错");
        }
        try {
            path = PropertiesUtil.getString(ConfigKey.PATH, ConfigKey.PATH_DEFAULT);
            toFileName = PropertiesUtil.getString(ConfigKey.TO_FILE, ConfigKey.TO_FILE_DEFAULT);
            charsetName = PropertiesUtil.getString(ConfigKey.CHAR_SET, ConfigKey.CHAR_SET_DEFAULT);
            containWord = PropertiesUtil.getString(ConfigKey.CONTAIN_WORD, ConfigKey.CONTAIN_WORD_DEFAULT);

            type = PropertiesUtil.getInteger(ConfigKey.TYPE, TaskType.SPLIT_LOG);
            param.put(ConfigKey.TYPE,type);

        }catch (Exception e){
            log.error("读取参数出错",e);
        }
    }
}
