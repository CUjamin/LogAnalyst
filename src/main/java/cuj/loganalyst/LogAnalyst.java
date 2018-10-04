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

    public static final String LOG_CONFIG_FILE_NAME="loganalyst_log4j.properties";

    public static final String CONFIG_FILE_NAME = "config.ini";
    public static final String FROM_FILE = "FROM";
    public static final String FROM_FILE_DEFAULT="";
    public static final String TO_FILE="TO";
    public static final String TO_FILE_DEFAULT="";

    public static final String SPLIT_WORD = ",";
    public static final String CHAR_SET="CHARSET";
    public static final String CHAR_SET_DEFAULT="GBK";
    public static final String CONTAIN_WORD="CONTAIN_WORD";
    public static final String CONTAIN_WORD_DEFAULT="";

    public static final String TYPE="TYPE";

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
        PropertyConfigurator.configure ( LogAnalyst.class.getClassLoader().getResource(LOG_CONFIG_FILE_NAME) );
        try{
            cfg = new FileReader("config.ini");
        }catch (FileNotFoundException fe)
        {

        }



        type = cfg.getInteger(TYPE, TaskType.SPLIT_LOG);
        String fromFileNamesStr = cfg.getString(FROM_FILE,FROM_FILE_DEFAULT);
        fileNames = fromFileNamesStr.split(SPLIT_WORD);
        toFileName = cfg.getString(TO_FILE,TO_FILE_DEFAULT);
        charsetName=cfg.getString(CHAR_SET,CHAR_SET_DEFAULT);
        containWord =cfg.getString(CONTAIN_WORD,CONTAIN_WORD_DEFAULT);
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
