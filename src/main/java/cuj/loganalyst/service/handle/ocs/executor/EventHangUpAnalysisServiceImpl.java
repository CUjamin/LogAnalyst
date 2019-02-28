package cuj.loganalyst.service.handle.ocs.executor;

import cuj.loganalyst.common.EventHangUpType;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import cuj.loganalyst.service.LogAnalysisServiceImpl;
import cuj.loganalyst.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cujamin on 2018/9/21.
 */
public class EventHangUpAnalysisServiceImpl implements EventHangUpAnalysisService{
    private final static Logger log = LoggerFactory.getLogger(LogAnalysisServiceImpl.class);
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();

    @Override
    public void handle(File[] files, String toFileName, String charsetName, String containWord)
    {
        log.info(" [start analysis ... ] ");
        List<String> tempList = new ArrayList<>();
        String titleBar = initTitleBar();
        tempList.add(titleBar);
        for(File file:files){
            List<String> dataList = inputService.readFromFile(file);
            if(null!=dataList&&dataList.size()>0) {
                int[] result = handleWord(dataList, containWord);

                String data = "";
                for (String type : EventHangUpType.EVENT_HANGUP_S.keySet()) {
                    data += ",";
                    data += result[EventHangUpType.EVENT_HANGUP_S.get(type)];
                }
                tempList.add(file.getName() + data);
            }
            log.info(" [ handle data number: "+tempList.size()+" ] ");
            outputService.outputInFile(tempList,toFileName+".csv",charsetName);
        }
        log.info(" [start analysis end ] ");
    }

    private String initTitleBar() {
        String titleBar=" 文件名 ";
        for(String type:EventHangUpType.EVENT_HANGUP_S.keySet())
        {
            titleBar+=",";
            titleBar+=type;
        }
        return titleBar;
    }

    private int[] handleWord(List<String> logList,String containWord) {
        int[] result = new int[9];
        for(String log:logList)
        {
            if(LogUtils.containWord(log,containWord))
            {
                for (String type:EventHangUpType.EVENT_HANGUP_S.keySet())
                {
                    if(LogUtils.containWord(log,type))
                    {
                        result[EventHangUpType.EVENT_HANGUP_S.get(type)]++;
                    }
                }
                result[8]++;
            }
        }
        return result;
    }
}
