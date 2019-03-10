package cuj.loganalyst.service;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.common.TaskType;
import cuj.loganalyst.service.handle.Handler;
import cuj.loganalyst.service.handle.MergeHandler;
import cuj.loganalyst.service.handle.WordRateHandler;
import cuj.loganalyst.service.handle.WordValuesStatisticsHandler;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalysisServiceImpl implements LogAnalysisService {

    private final static Logger log = LoggerFactory.getLogger(LogAnalysisServiceImpl.class);

    private Handler handler;
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();

    private int type;
    private String toFileName;
    private String charsetName;
    File[] files = null;

    @Override
    public void handle(String path, String toFileName, String charsetName, Map<String, Object> params) {
        this.toFileName = toFileName;
        this.charsetName = charsetName;
        type = (int)params.get(ConfigKey.TYPE);


        if(initFiles(path)){
            Map<String,List<String>> dataListMap = initData();
            List resultData = null;
            switch (type)
            {
                case TaskType.MERGE:{
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

            handler.handle(dataListMap,params,outputService);
        }
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
        }
        else {
            log.info("path 为空");
            result = false;
        }
        return result;
    }

    private Map<String,List<String>> initData(){
        Map<String,List<String>> dataListMap = new HashMap<>();
        for(int i=0;i<files.length;++i){
            List<String> dataList = inputService.readFromFile(files[i]);
            dataListMap.put(files[i].getName(),dataList);
        }
        return dataListMap;
    }
}
