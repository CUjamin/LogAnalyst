package cuj.loganalyst.service.handle;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Auther: cujamin
 * @Date: 2019/3/4 12:18
 * @Description:
 */
public class WordValuesStatisticsHandler implements Handler{

    private final static Logger log = LoggerFactory.getLogger(WordValuesStatisticsHandler.class);

    public void handle(
            Map<String,List<String>> dataListMap,
            Map<String, Object> params,
            OutputService outputService){

        List<String> resultDataList = new LinkedList<>();
        String wordValuesStr = (String)params.get(ConfigKey.WORD_VALUES);
        String separator = (String)params.get(ConfigKey.SEPARATOR);
        String wordKey = (String)params.get(ConfigKey.WORD_KEY);

        List<String> wordValueList = parseWordValuesStr(wordValuesStr,separator);
        System.out.println(wordValueList+";"+separator);

        log.info(" [start analysis ... ] ");
        String titleBar = initTitleBar(wordValueList);
        resultDataList.add(titleBar);

        Iterator<Map.Entry<String, List<String>>> it = dataListMap.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<String, List<String>> entry = it.next();

            Map<String ,Integer> resultMap = countWordValues(entry.getValue(), wordKey,wordValueList);

            String data = "";
            for (String wordValue : resultMap.keySet()) {
                data += ",";
                data += resultMap.get(wordValue);
            }
            resultDataList.add(entry.getKey()+data);
        }

        log.info(" [ handle data number: "+resultDataList.size()+" ] ");

        log.info(" [start analysis end ] ");
        String suffix = (String)params.get(ConfigKey.SUFFIX);
        String filename = (String)params.get(ConfigKey.TO_FILE)+"."+suffix;
        outputService.outputInFile(resultDataList, filename,(String)params.get(ConfigKey.CHAR_SET));
    }

    private List<String> parseWordValuesStr(String wordValuesStr,String separator){
        List<String> wordValueList = new LinkedList<>();
        String[] wordValues = wordValuesStr.split(separator);
        for(String wordValue:wordValues){
            wordValueList.add(wordValue);
        }
        return wordValueList;
    }

    private String initTitleBar(List<String> wordValueList) {
        String titleBar=" 文件名 ";
        for(String wordValue: wordValueList)
        {
            titleBar+=",";
            titleBar+=wordValue;
        }
        return titleBar;
    }

    private Map<String ,Integer> countWordValues(List<String> dataList,String wordKey , List<String> wordValueList) {

        Map<String ,Integer> valueCountMap = new HashMap<>();

        for(String data:dataList)
        {
            if(LogUtils.containWord(data,wordKey))
            {
                for (String wordValue:wordValueList)
                {
                    if(LogUtils.containWord(data,wordValue))
                    {
                        if(valueCountMap.containsKey(wordValue)){
                            int count = valueCountMap.get(wordValue);
                            valueCountMap.put(wordValue,++count);
                        }else {
                            valueCountMap.put(wordValue,1);
                        }
                    }
                }
            }
        }
        return valueCountMap;
    }
}
