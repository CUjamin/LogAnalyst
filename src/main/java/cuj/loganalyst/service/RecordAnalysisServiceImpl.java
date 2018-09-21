package cuj.loganalyst.service;

import cuj.loganalyst.domain.Record;
import cuj.loganalyst.input.InputService;
import cuj.loganalyst.input.InputServiceImpl;
import cuj.loganalyst.output.OutputService;
import cuj.loganalyst.output.OutputServiceImpl;
import cuj.loganalyst.util.RecordUtil;
import cuj.loganalyst.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cujamin on 2018/9/20.
 */
public class RecordAnalysisServiceImpl implements RecordAnalysisService {
    private final static Logger log = LoggerFactory.getLogger(LogAnalysisServiceImpl.class);
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    private final static String spitKey1 = " INFO";

    @Override
    public void handle(String fromFileName,String toFileName,String charsetName,String containWord,int resultType) {
        log.info(" [start analysis ... ] ");
        List<String> dataList = inputService.readFromFile(fromFileName);
        if(null!=dataList&&dataList.size()>0)
        {
            List<String> tempList = new ArrayList<String>();
            for(String tempStr:dataList)
            {
                if(containWord(tempStr,containWord))
                {
                    handleRecord(tempList,tempStr,resultType);
                }
            }
            log.info(" [ handle data number: "+tempList.size()+" ] ");
            if(tempList.size()>0){
                outputService.outputInFile(tempList,toFileName,charsetName);
            }else {
                log.info(" [结果为空 ] ");
            }
        }
        log.info(" [start analysis end ] ");
    }

    private void handleRecord(List<String> tempList,String tempStr,int resultType) {
        String[] tempStrArray = tempStr.split(spitKey1);

        if (tempStrArray.length>=2)
        {
            String releasedTimeStr = tempStrArray[0];
            String otherStr = tempStrArray[1];

            Record record = RecordUtil.getRecord(otherStr);
            if(0==resultType) {
                //全部处理
                tempList.add(getResult(releasedTimeStr , record));
            }
            else if(null!=record&&resultType==record.getResultType()) {
                //只处理满足resultType的
                tempList.add(getResult(releasedTimeStr , record));
            }
        }
    }

    private String getResult(String releasedTimeStr , Record record)
    {
        long releasedTime = TimeUtil.getTimeStamp(releasedTimeStr);
        long callCustomerTime = Long.parseLong(record.getCallCustomerTime());
        String callCustomerTimeStr = TimeUtil.getTimeStr(callCustomerTime);
        double diffTime = (double) (releasedTime-callCustomerTime)/1000.0;
        String result="releasedTimeStr:"+releasedTimeStr+";call_customer_timeStr:"+callCustomerTimeStr+"releasedTime:"+releasedTime+";call_customer_time:"+callCustomerTime+";diffTime:"+diffTime+"s - Record:"+record.toString();
        return result;
    }

    private boolean containWord(String data,String word)
    {
        String pattern = String.format(".*%s.*", word);
        return Pattern.matches(pattern, data);
    }
}
