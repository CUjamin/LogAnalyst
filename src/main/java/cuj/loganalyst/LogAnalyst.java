package cuj.loganalyst;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.common.LogAnalysisService;
import cuj.loganalyst.service.common.LogAnalysisServiceImpl;
import cuj.loganalyst.service.common.marge.MergeService;
import cuj.loganalyst.service.common.marge.MergeServiceImpl;
import cuj.loganalyst.service.ocs.executor.EventHangUpAnalysisService;
import cuj.loganalyst.service.ocs.executor.EventHangUpAnalysisServiceImpl;
import cuj.loganalyst.service.ocs.manager.RecordAnalysisService;
import cuj.loganalyst.service.ocs.manager.RecordAnalysisServiceImpl;
import cuj.loganalyst.util.PropertiesUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalyst {
    private final static Logger log = LoggerFactory.getLogger(LogAnalyst.class);
    private static LogAnalysisService logAnalysisService = null;
    private static RecordAnalysisService recordAnalysisService;
    private static EventHangUpAnalysisService eventHangUpAnalysisService;
    private static MergeService mergeService;
    private static int type ;
    private static String path;
    private static String charsetName;
    private static String containWord;
    private static String toFileName;
    private static File[] files = null;

    public static void main(String[] args) {

        initParam();

        if(!"".equalsIgnoreCase(path)){
            initFiles();
        }else {
            log.info("path 为空");
        }

        if(null!=files){
            handleFiles();
        }else {
            log.info("files 为空");
        }
    }

    private static void initParam() {
        PropertyConfigurator.configure(LogAnalyst.class.getClassLoader().getResource(ConfigKey.LOG_CONFIG_FILE_NAME));
        try {
            PropertiesUtil.initPropertiesFile(ConfigKey.CONFIG_FILE_NAME);
        } catch (IOException e) {
            log.info("读取配置文件出错");
        }
        try {
            type = PropertiesUtil.getInteger(ConfigKey.TYPE, TaskType.SPLIT_LOG);
            path = PropertiesUtil.getString(ConfigKey.PATH, ConfigKey.PATH_DEFAULT);
            toFileName = PropertiesUtil.getString(ConfigKey.TO_FILE, ConfigKey.TO_FILE_DEFAULT);
            charsetName = PropertiesUtil.getString(ConfigKey.CHAR_SET, ConfigKey.CHAR_SET_DEFAULT);
            containWord = PropertiesUtil.getString(ConfigKey.CONTAIN_WORD, ConfigKey.CONTAIN_WORD_DEFAULT);
        }catch (Exception e){
            log.error("读取参数出错",e);
        }
    }

    private static void initFiles(){
        log.info(" [get files from the path:"+path+"] ");
        File arrayFiles = new File(path);
        files = arrayFiles.listFiles();
        for(File file:files){
            log.info(file.getName());
        }
        log.info(" [get files from the path:"+path+" OK ] ");
    }

    private static void handleFiles(){
        switch (type)
        {
            case TaskType.SPLIT_LOG:        getLog();               break;
            case TaskType.RECORD_ANALYSIS:  analystRecordTime();    break;
            case TaskType.EVENT_ANALYSIS:   eventHangUpAnalysis();  break;
            case TaskType.MERGE:            mergeLogFile();         break;
            default:break;
        }
    }

    public static void getLog() {
        logAnalysisService = new LogAnalysisServiceImpl();
        if (!"".equalsIgnoreCase(path)) {
            File arrayFiles = new File(path);
            File[] files = arrayFiles.listFiles();
            for (File file : files) {
                log.info(" [logAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
                toFileName = String.format("%s-%s.log", file.getName(), containWord);
                logAnalysisService.handle(file, toFileName, charsetName, containWord);
            }
        }
    }


    private static void analystRecordTime() {
        recordAnalysisService = new RecordAnalysisServiceImpl();
        String resultsStr = null;
        try{
            resultsStr = PropertiesUtil.getString("RESULT_TYPES", "0");
        }catch (Exception e){

        }
        String[] results = resultsStr.split(",");
        if (!"".equalsIgnoreCase(path)) {
            for (File file : files) {
                log.info(" [recordAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
                for (int i = 0; i < results.length; ++i) {
                    int resultType = Integer.valueOf(results[i]);
                    toFileName = String.format("%s-%s-resultType-%s.log", file.getName(), containWord, resultType);
                    recordAnalysisService.handle(file, toFileName, charsetName, containWord, resultType);
                }
            }
        }
    }

    private static void eventHangUpAnalysis() {
        eventHangUpAnalysisService = new EventHangUpAnalysisServiceImpl();
        log.info(" [eventHangUpAnalysis-path:"+path+";to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
        eventHangUpAnalysisService.handle(files,toFileName,charsetName,containWord);
    }

    private static void mergeLogFile() {
        mergeService = new MergeServiceImpl();
        log.info(" [eventHangUpAnalysis-path:"+path+";to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
        mergeService.handle(files[0],files[1],toFileName,charsetName);
    }
}
