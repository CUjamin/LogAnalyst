package cuj.loganalyst.service;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.handle.common.marge.MergeService;
import cuj.loganalyst.service.handle.common.marge.MergeServiceImpl;
import cuj.loganalyst.service.handle.common.sifte.SifteService;
import cuj.loganalyst.service.handle.common.sifte.SifteServiceImpl;
import cuj.loganalyst.service.handle.ocs.executor.EventHangUpAnalysisService;
import cuj.loganalyst.service.handle.ocs.manager.RecordAnalysisService;
import cuj.loganalyst.service.handle.ocs.executor.EventHangUpAnalysisServiceImpl;
import cuj.loganalyst.service.handle.ocs.manager.RecordAnalysisServiceImpl;
import cuj.loganalyst.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalysisServiceImpl implements LogAnalysisService {

    private final static Logger log = LoggerFactory.getLogger(SifteServiceImpl.class);

    private int type;
    private String toFileName;
    private String charsetName;
    private String containWord;
    private Map<String, Object> params;
    File[] files = null;
    @Override
    public void handle(String path, String toFileName, String charsetName,String containWord,Map<String, Object> params) {
        this.toFileName = toFileName;
        this.charsetName = charsetName;
        this.containWord =containWord;
        this.params = params;
        type = (int)params.get(ConfigKey.TYPE);

        log.info(" [get files from the path:"+path+"] ");
        if (!"".equalsIgnoreCase(path)){
            File arrayFiles = new File(path);
            files = arrayFiles.listFiles();
            for(File file:files){
                log.info(file.getName());
            }

            log.info(" [get files from the path:"+path+" OK ] ");


            handleFiles(files);
        }
        else {

        }
    }

    private void handleFiles(File[] files){
        List<String> list = null;
        switch (type)
        {
            case TaskType.SPLIT_LOG:        getLog(files);               break;
            case TaskType.RECORD_ANALYSIS:  analystRecordTime(files);    break;
            case TaskType.EVENT_ANALYSIS:   eventHangUpAnalysis(files);  break;
            case TaskType.MERGE:            mergeLogFile(files);         break;
            default:break;
        }
    }

    public void getLog(File[] files) {
        for(File file:files){
            SifteService sifteService = new SifteServiceImpl();
            log.info(" [logAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
            toFileName = String.format("%s-%s.log", file.getName(), containWord);
            sifteService.handle(file,toFileName,charsetName, containWord);
        }
    }


    private void analystRecordTime(File[] files) {
        RecordAnalysisService recordAnalysisService = new RecordAnalysisServiceImpl();
        String resultsStr = null;
        try{
            resultsStr = PropertiesUtil.getString("RESULT_TYPES", "0");
        }catch (Exception e){

        }
        String[] results = resultsStr.split(",");

        for (File file : files) {
            log.info(" [recordAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
            for (int i = 0; i < results.length; ++i) {
                int resultType = Integer.valueOf(results[i]);
                toFileName = String.format("%s-%s-resultType-%s.log", file.getName(), containWord, resultType);
                recordAnalysisService.handle(file, toFileName, charsetName, containWord, resultType);
            }

        }
    }

    private void eventHangUpAnalysis(File[] files) {
        EventHangUpAnalysisService eventHangUpAnalysisService = new EventHangUpAnalysisServiceImpl();
        log.info(" [eventHangUpAnalysis-to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
        eventHangUpAnalysisService.handle(files,toFileName,charsetName,containWord);
    }

    private void mergeLogFile(File[] files) {
        MergeService mergeService = new MergeServiceImpl();
        log.info(" [eventHangUpAnalysis-to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
        mergeService.handle(files[0],files[1],toFileName,charsetName);
    }
}
