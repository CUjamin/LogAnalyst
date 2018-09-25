package cuj.loganalyst;


import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.*;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalyst {
    private final static Logger log = LoggerFactory.getLogger(LogAnalyst.class);
    private static LogAnalysisService logAnalysisService = null;
    private static RecordAnalysisService recordAnalysisService;
    private static EventHangUpAnalysisService eventHangUpAnalysisService;
    private static FileReader cfg;
    private static int type ;
    private static String[] fileNames;
    private static String charsetName;
    private static String containWord;
    private static String toFileName;

    public static void main(String[] args) {
        init();
        switch (type)
        {
            case TaskType.SPLIT_LOG: getLog();break;
            case TaskType.RECORD_ANALYSIS: analystRecordTime();break;
            case TaskType.EVENT_ANALYSIS:eventHangUpAnalysis();break;
            default:break;
        }
    }

    private static void init() {
        PropertyConfigurator.configure ( LogAnalyst.class.getClassLoader().getResource(ConfigKey.LOG_CONFIG_FILE_NAME) );
        try{
            cfg = new FileReader("config.ini");
        }catch (FileNotFoundException fe)
        {

        }
        type = cfg.getInteger(ConfigKey.TYPE, TaskType.SPLIT_LOG);
        String fromFileNamesStr = cfg.getString(ConfigKey.FROM_FILE,ConfigKey.FROM_FILE_DEFAULT);
        fileNames = fromFileNamesStr.split(ConfigKey.SPLIT_WORD);
        toFileName = cfg.getString(ConfigKey.TO_FILE,ConfigKey.TO_FILE_DEFAULT);
        charsetName=cfg.getString(ConfigKey.CHAR_SET,ConfigKey.CHAR_SET_DEFAULT);
        containWord =cfg.getString(ConfigKey.CONTAIN_WORD,ConfigKey.CONTAIN_WORD_DEFAULT);
    }

    public static void getLog()
    {
        logAnalysisService = new LogAnalysisServiceImpl();
        for(String fileNme:fileNames){
            log.info(" [logAnalysisService-form:"+fileNme+";to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
            toFileName = String.format("%s-%s.log",fileNme,containWord);
            logAnalysisService.handle(fileNme,toFileName,charsetName,containWord);
        }
    }

    private static void analystRecordTime() {
        recordAnalysisService  =new RecordAnalysisServiceImpl();
        String resultsStr = fileReader.getString("RESULT_TYPES","0");
        String[] results = resultsStr.split(",");
        for(String fileName:fileNames){
            log.info(" [recordAnalysisService-form:"+fileName+";to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
            for(int i=0;i<results.length;++i)
            {
                int resultType = Integer.valueOf(results[i]);
                toFileName = String.format("%s-%s-resultType-%s.log",fileName,containWord,resultType);
                recordAnalysisService.handle(fileName,toFileName,charsetName,containWord,resultType);
            }
        }
    }

    private static void eventHangUpAnalysis() {
        eventHangUpAnalysisService = new EventHangUpAnalysisServiceImpl();
        for(String fileName:fileNames){
            log.info(" [eventHangUpAnalysis-form:"+fileName+";to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
            eventHangUpAnalysisService.handle(fileName,toFileName,charsetName,containWord);
        }
    }
}
