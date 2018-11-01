package cuj.loganalyst.service.handle.common.marge;

import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import cuj.loganalyst.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.ParseException;
import java.util.LinkedList;
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
        log.info(" [start merge two file... ] ");
        List<String> dataListA = inputService.readFromFile(fileA);
        List<String> dataListB = inputService.readFromFile(fileB);
        outputService.outputInFile(mergeTwoList(dataListA,dataListB), String.format("%s.log", toFileName),charsetName);
        log.info(" [ merge two file end ] ");
    }

    @Override
    public void handle(File[] files, String toFileName, String charsetName) {
        log.info(" [start merge any file... ] ");
        List<List<String>> dataLists = new LinkedList<List<String>>();
        for(int i=0;i<files.length;++i){
            List<String> dataList = inputService.readFromFile(files[i]);
            dataLists.add(dataList);
        }

        while(dataLists.size()>1){
            List<String> listA = dataLists.remove(0);
            List<String> listB = dataLists.remove(0);
            List<String> newList = mergeTwoList(listA,listB);
            dataLists.add(newList);
        }
        outputService.outputInFile(dataLists.remove(0), String.format("%s.log", toFileName),charsetName);
        log.info(" [ merge any file end ] ");

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
