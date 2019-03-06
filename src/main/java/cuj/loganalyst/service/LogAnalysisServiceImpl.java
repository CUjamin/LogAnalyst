package cuj.loganalyst.service;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.handle.common.Handler;
import cuj.loganalyst.service.handle.common.MergeHandler;
import cuj.loganalyst.service.handle.common.WordRateHandler;
import cuj.loganalyst.service.handle.common.WordValuesStatisticsHandler;
import cuj.loganalyst.service.handle.common.sifte.SifteServiceImpl;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalysisServiceImpl implements LogAnalysisService {

    private final static Logger log = LoggerFactory.getLogger(SifteServiceImpl.class);

    private Handler handler;
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();

    private int type;
    private String toFileName;
    private String charsetName;
    private Map<String, Object> params;
    File[] files = null;
//    @Override
//    public void handle(String path, String toFileName, String charsetName,String containWord,Map<String, Object> params) {
//        this.toFileName = toFileName;
//        this.charsetName = charsetName;
//        this.containWord =containWord;
//        this.params = params;
//        type = (int)params.get(ConfigKey.TYPE);
//    }

    @Override
    public void handle(String path, String toFileName, String charsetName, Map<String, Object> params) {
        this.toFileName = toFileName;
        this.charsetName = charsetName;
        this.params = params;
        type = (int)params.get(ConfigKey.TYPE);
        String suffix = (String)params.get(ConfigKey.SUFFIX);

        if(initFiles(path)){
            List<List<String>> dataLists = initData();
            List resultData = null;
            switch (type)
            {
                case TaskType.MERGE:            {
                    handler = new MergeHandler();
                    break;
                }
                case  TaskType.TIME_DIFF:{
                    handler = new WordRateHandler();
                    break;
                }
                case TaskType.STATISTICS:{
                    handler = new WordValuesStatisticsHandler();
                    break;
                }
                default:break;
            }

            resultData = handler.handle(dataLists,params);

            outPutData2File(resultData,suffix);
        }
    }

    private void outPutData2File(List resultData,String suffix) {
        String filename = toFileName+"."+suffix;
        outputService.outputInFile(resultData,filename,charsetName);
    }

    private boolean initFiles(String path){
        boolean result = true;
        log.info(" [get files from the path:"+path+"] ");
        if (!"".equalsIgnoreCase(path)){
            File arrayFiles = new File(path);
            files = arrayFiles.listFiles();
            for(File file:files){
                log.info(file.getName());
            }

            log.info(" [get files from the path:"+path+" OK ] ");

            List<String> list = null;

        }
        else {
            log.info("path 为空");
            result = false;
        }
        return result;
    }

    private List<List<String>> initData(){
        List<List<String>> dataLists = new LinkedList<List<String>>();
        for(int i=0;i<files.length;++i){
            List<String> dataList = inputService.readFromFile(files[i]);
            dataLists.add(dataList);
        }
        return dataLists;
    }

//    public void getLog() {
//        for(File file:files){
//            SifteService sifteService = new SifteServiceImpl();
//            log.info(" [logAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
//            toFileName = String.format("%s-%s", file.getName(), containWord);
//            sifteService.handle(file,toFileName,charsetName, containWord);
//        }
//    }


//    private void analystRecordTime() {
//        RecordAnalysisService recordAnalysisService = new RecordAnalysisServiceImpl();
//        String resultsStr = null;
//        try{
//            resultsStr = PropertiesUtil.getString("RESULT_TYPES", "0");
//        }catch (Exception e){
//
//        }
//        String[] results = resultsStr.split(",");
//
//        for (File file : files) {
//            log.info(" [recordAnalysisService-form:" + file.getName() + ";to:" + toFileName + ";charset:" + charsetName + ";containWord:" + containWord + "] ");
//            for (int i = 0; i < results.length; ++i) {
//                int resultType = Integer.parseInt(results[i]);
//                toFileName = String.format("%s-%s-resultType-%s", file.getName(), containWord, resultType);
//                recordAnalysisService.handle(file, toFileName, charsetName, containWord, resultType);
//            }
//        }
//    }

//    private void eventHangUpAnalysis() {
//        EventHangUpAnalysisService eventHangUpAnalysisService = new EventHangUpAnalysisServiceImpl();
//        log.info(" [eventHangUpAnalysis-to:"+toFileName+";charset:"+charsetName+";containWord:"+containWord+"] ");
//        eventHangUpAnalysisService.handle(files,toFileName,charsetName,containWord);
//    }
//
//    private void timeDIff(){
//        TimeDifferenceService timeDifferenceService = new TimeDifferenceServiceImpl();
//        log.info(" [ timeDIff - charset : "+charsetName+" ; containWord : "+containWord+" ] ");
//        for (File file : files){
//            toFileName=String.format("%s-time-diff", file.getName());
//            timeDifferenceService.handle(file,toFileName,charsetName,containWord);
//        }
//    }
}
