package cuj.loganalyst;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.LogAnalysisService;
import cuj.loganalyst.service.LogAnalysisServiceImpl;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
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

    private static String path;
    private static String charsetName;
    private static String toFileName;
    private static Map<String,Object> param = new HashMap<String,Object>();

    public static void main(String[] args) {

        initParam();

        logAnalysisService.handle(path,toFileName,charsetName,param);
    }

    private static void initParam() {
        //init log
        File logConfigFile = new File(ConfigKey.LOG_CONFIG_FILE_NAME);
        if(logConfigFile.exists()){
            PropertyConfigurator.configure(ConfigKey.LOG_CONFIG_FILE_NAME);
            log.info("use outside log config file : "+ConfigKey.LOG_CONFIG_FILE_NAME);
        }else {
            PropertyConfigurator.configure(LogAnalyst.class.getClassLoader().getResource(ConfigKey.LOG_CONFIG_FILE_NAME));
            log.info("use inside log config file : "+LogAnalyst.class.getClassLoader().getResource(ConfigKey.LOG_CONFIG_FILE_NAME));
        }
        //init cofnig
        try {
            File configFile = new File(ConfigKey.CONFIG_FILE_NAME);
            if(configFile.exists()){
                log.info("use outside config file : "+ConfigKey.CONFIG_FILE_NAME);
                PropertiesUtil.initPropertiesFile(ConfigKey.CONFIG_FILE_NAME);
            }else {
                log.info("use inside config file : "+LogAnalyst.class.getClassLoader().getResource(ConfigKey.CONFIG_FILE_NAME).toExternalForm());
                PropertiesUtil.initPropertiesFile(LogAnalyst.class.getClassLoader().getResource(ConfigKey.CONFIG_FILE_NAME).toExternalForm());
            }
        } catch (IOException e) {
            log.error("read config file IOException ",e);
        }
        // read config params
        try {
            path = PropertiesUtil.getString(ConfigKey.PATH, ConfigKey.PATH_DEFAULT);

            toFileName = PropertiesUtil.getString(ConfigKey.TO_FILE, ConfigKey.TO_FILE_DEFAULT);
            param.put(ConfigKey.TO_FILE,toFileName);

            charsetName = PropertiesUtil.getString(ConfigKey.CHAR_SET, ConfigKey.CHAR_SET_DEFAULT);
            param.put(ConfigKey.CHAR_SET,charsetName);

            String filteringWord = PropertiesUtil.getString(ConfigKey.FILTERING_WORD,"");
            param.put(ConfigKey.FILTERING_WORD,filteringWord);

            String wordKey = PropertiesUtil.getString(ConfigKey.WORD_KEY,ConfigKey.WORD_KEY_DEFAULT);
            param.put(ConfigKey.WORD_KEY,wordKey);

            String wordValuesStr = PropertiesUtil.getString(ConfigKey.WORD_VALUES,"");
            param.put(ConfigKey.WORD_VALUES,wordValuesStr);

            String separator = PropertiesUtil.getString(ConfigKey.SEPARATOR,ConfigKey.SEPARATOR_DEFAULT);
            param.put(ConfigKey.SEPARATOR,separator);

            int type = PropertiesUtil.getInteger(ConfigKey.TYPE, TaskType.SPLIT_LOG);
            param.put(ConfigKey.TYPE,type);

            String suffix = PropertiesUtil.getString(ConfigKey.SUFFIX, ConfigKey.SUFFIX_DEFAULT);
            param.put(ConfigKey.SUFFIX,suffix);

        }catch (Exception e){
            log.error("read config params Exception",e);
        }
    }
}
