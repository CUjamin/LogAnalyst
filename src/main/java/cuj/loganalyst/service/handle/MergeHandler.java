package cuj.loganalyst.service.handle;


import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: cujamin
 * @Date: 2019/3/4 11:17
 * @Description:
 */
public class MergeHandler implements Handler{

    private final static Logger log = LoggerFactory.getLogger(MergeHandler.class);
    private final static String spitKey1 = " INFO";

    public void handle(
            Map<String,List<String>> dataListMap,
            Map<String, Object> params,
            OutputService outputService){

        List<List<String>> dataLists = new LinkedList<>();
        for(String fileName:dataListMap.keySet()){
            dataLists.add(dataListMap.get(fileName));
        }

        log.info(" [start merge any files... ] ");

        if(dataLists.size()>=1){
            while(dataLists.size()>1){
                List<String> listA = dataLists.remove(0);
                List<String> listB = dataLists.remove(0);
                List<String> newList = mergeTwoList(listA,listB);
                dataLists.add(newList);
            }
            String suffix = (String)params.get(ConfigKey.SUFFIX);
            String filename = (String)params.get(ConfigKey.TO_FILE)+"."+suffix;
            outputService.outputInFile(dataLists.remove(0), filename,(String)params.get(ConfigKey.CHAR_SET));
        }else {
            return ;
        }
    }

    public List<String> mergeTwoList(List<String> listA,List<String> listB){
        List<String> outPutList = new LinkedList<String>();
        if(listA.isEmpty()){
            outPutList.addAll(listB);
            return outPutList;
        }
        if(listB.isEmpty()){
            outPutList.addAll(listA);
            return outPutList;
        }

        List<String> dataListA = new LinkedList<String>();
        dataListA.addAll(listA);
        List<String> dataListB = new LinkedList<String>();
        dataListB.addAll(listB);

        String dataA = dataListA.remove(0);
        String dataB = dataListB.remove(0);
        while(true){
            if(dateAisEarlier(dataA,dataB)){
                outPutList.add(dataA);
                if(!dataListA.isEmpty()){
                    dataA = dataListA.remove(0);
                }else {
                    outPutList.add(dataB);
                    outPutList.addAll(dataListB);
                    break;
                }
            }else {
                outPutList.add(dataB);
                if(!dataListB.isEmpty()){
                    dataB = dataListB.remove(0);
                }else {
                    outPutList.add(dataA);
                    outPutList.addAll(dataListA);
                    break;
                }
            }
        }
        return outPutList;
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
        if(0L==timeLong){
            log.error("time error");
        }
        return timeLong;
    }
}
