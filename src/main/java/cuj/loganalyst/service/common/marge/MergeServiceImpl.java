package cuj.loganalyst.service.common.marge;

import cuj.loganalyst.domain.Record;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import cuj.loganalyst.util.RecordUtil;
import cuj.loganalyst.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cujamin
 * @Date: 2018/10/26 13:14
 * @Description:
 */
public class MergeServiceImpl implements MergeService {

    private final static Logger log = LoggerFactory.getLogger(MergeServiceImpl.class);
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    private final static String spitKey1 = " INFO";

    @Override
    public void handle(File fileA, File fileB, String toFileName, String charsetName) {
        log.info(" [start merge ... ] ");
        List<String> dataListA = inputService.readFromFile(fileA);
        List<String> dataListB = inputService.readFromFile(fileB);
        List<String> outPutList = new ArrayList<String>();
        String dataA = dataListA.remove(0);
        String dataB = dataListB.remove(0);
        boolean mergeRunning = true;
        while(mergeRunning){
            if(dataListA.isEmpty()||dataListB.isEmpty()){

                if(dateAisEarlier(dataA,dataB)) {
                    outPutList.add(dataA);
                    outPutList.add(dataB);
                }else {
                    outPutList.add(dataB);
                    outPutList.add(dataA);
                }

                if(dataListA.isEmpty()){
                    outPutList.addAll(dataListB);
                }
                if(dataListB.isEmpty()){
                    outPutList.addAll(dataListA);
                }
                break;
            }


            if(dateAisEarlier(dataA,dataB)){
                outPutList.add(dataA);
                dataA = dataListA.remove(0);
            }else {
                outPutList.add(dataB);
                dataB = dataListB.remove(0);
            }
        }
        outputService.outputInFile(outPutList,toFileName,charsetName);
        log.info(" [ merge end ] ");
    }
    private boolean dateAisEarlier(String dataA,String dataB){
        return (getTime(dataA)-getTime(dataB)<0);
    }
    private long getTime(String tempStr)
    {
        String[] tempStrArray = tempStr.split(spitKey1);
        long timeLong = 0L;

        if (tempStrArray.length>=2)
        {
            String timeStr = tempStrArray[0];
            try{
                timeLong = TimeUtil.getTimeStamp(timeStr);
            }catch (ParseException pe){
                log.error("解析时间字符串出错",pe);
            }
        }
        if(0L==timeLong)log.error("time error");
        return timeLong;
    }
}
