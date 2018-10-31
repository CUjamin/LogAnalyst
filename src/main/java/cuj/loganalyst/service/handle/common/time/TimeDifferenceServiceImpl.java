package cuj.loganalyst.service.handle.common.time;

import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import cuj.loganalyst.util.LogUtils;
import cuj.loganalyst.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: cujamin
 * @Date: 2018/10/31 11:53
 * @Description:
 */
public class TimeDifferenceServiceImpl implements TimeDifferenceService {
    private final static Logger log = LoggerFactory.getLogger(TimeDifferenceServiceImpl.class);

    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    private String pattern;
    private final static String spitKey1 = " INFO";
    @Override
    public void handle(File file, String toFileName, String charsetName, String containWord) {
        log.info(" [start analysis ... ] ");
        List<String> dataList = inputService.readFromFile(file);
        List<String> tempList = new ArrayList<String>();
        pattern = LogUtils.getPatternOrder(containWord);
        log.info("pattern:"+pattern);
        if(null!=dataList&&dataList.size()>0)
        {
            boolean isFirst = true;
            String tempA="";
            String tempB="";
            for(String tempStr:dataList)
            {
                if(LogUtils.containWordOrder(tempStr, pattern))
                {
                    if(isFirst){
                        tempB = tempStr;
                        isFirst = false;
                        continue;
                    }else {
                        tempA = tempB;
                        tempB = tempStr;
                    }
                    tempList.add(handleWord(tempA, tempB));
                }
            }
            log.info(" [ handle data number: "+tempList.size()+" ] ");
        }
        log.info(" [start analysis end ] ");
        outputService.outputInFile(tempList, String.format("%s.csv", toFileName),charsetName);
    }

    private String handleWord(String tempStrA, String tempStrB) {
        String timeStrA = getTimeStr(tempStrA);
        String timeStrB = getTimeStr(tempStrB);
        long timeDif = 0L;
        try{
            timeDif = TimeUtil.difTime(timeStrA , timeStrB);
        }catch (ParseException pe){
            log.error("解析时间串发生异常",pe);
        }
        String strA = timeStrA.replace(",",".");
        String strB = timeStrB.replace(",",".");
        return String.format("%s,%s,%d", strA, strB, timeDif);
    }

    private String getTimeStr(String tempStr)
    {
        String[] tempStrArray = tempStr.split(spitKey1);
        return tempStrArray[0];
    }
}
