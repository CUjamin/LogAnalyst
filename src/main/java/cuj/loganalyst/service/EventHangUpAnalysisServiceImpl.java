package cuj.loganalyst.service;

import cuj.loganalyst.common.EventHangUpType;
import cuj.loganalyst.input.InputService;
import cuj.loganalyst.input.InputServiceImpl;
import cuj.loganalyst.output.OutputService;
import cuj.loganalyst.output.OutputServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by cujamin on 2018/9/21.
 */
public class EventHangUpAnalysisServiceImpl implements EventHangUpAnalysisService{
    private final static Logger log = LoggerFactory.getLogger(LogAnalysisServiceImpl.class);
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();

    @Override
    public void handle(String fromFileName,String toFileName,String charsetName,String containWord)
    {
        log.info(" [start analysis ... ] ");
        List<String> dataList = inputService.readFromFile(fromFileName);
        if(null!=dataList&&dataList.size()>0)
        {
            List<String> tempList = new ArrayList<>();
            int[]result = handleWord(dataList,containWord);
            Map<String ,Integer> resultMap = new HashMap<>();
            for(String type:EventHangUpType.EVENT_HANGUP_S.keySet())
            {
                resultMap.put(type,result[EventHangUpType.EVENT_HANGUP_S.get(type)]);
            }
            tempList.add(fromFileName+"-"+resultMap.toString());
            log.info(" [ handle data number: "+tempList.size()+" ] ");
            outputService.outputInFile(tempList,toFileName,charsetName);
        }
        log.info(" [start analysis end ] ");
    }

    private int[] handleWord(List<String> logList,String containWord) {
        int[] result = new int[9];
        for(String log:logList)
        {
            if(containWord(log,containWord))
            {
                for (String type:EventHangUpType.EVENT_HANGUP_S.keySet())
                {

                    if(containWord(log,type))
                    {
                        result[EventHangUpType.EVENT_HANGUP_S.get(type)]++;
                    }

                }
                result[8]++;
            }

        }
        return result;
    }

    private boolean containWord(String data,String word)
    {
        String pattern = String.format(".*%s.*", word);
        return Pattern.matches(pattern, data);
    }
}
