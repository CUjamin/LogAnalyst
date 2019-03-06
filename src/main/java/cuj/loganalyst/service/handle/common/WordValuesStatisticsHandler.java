package cuj.loganalyst.service.handle.common;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import cuj.loganalyst.common.ConfigKey;
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

    public List<String> handle(List<List<String>> dataLists, Map<String, Object> params){
        List<String> resultDataList = new LinkedList<>();
        String wordValuesStr = (String)params.get(ConfigKey.WORD_VALUES);
        String separator = (String)params.get(ConfigKey.SEPARATOR);
        String wordKey = (String)params.get(ConfigKey.WORD_KEY);
//        String wordKey = "CONTAIN_WORD";
//
//        String wordValuesStr = "aaaaaa,aaaaab";
//        String separator = ",";
        Set<String> wordValueSet = parseWordValuesStr(wordValuesStr,separator);
        System.out.println(wordValueSet+";"+separator);

        log.info(" [start analysis ... ] ");
        String titleBar = initTitleBar();
        resultDataList.add(titleBar);
        for(List<String> dataList:dataLists){
            if(null!=dataList&&dataList.size()>0) {
                Map<String ,Integer> resultMap = countWordValues(dataList, wordKey,wordValueSet);

                String data = "";
                for (String wordValue : resultMap.keySet()) {
                    data += ",";
                    data += wordValue+":"+resultMap.get(wordValue);
                }
                resultDataList.add(data);
            }
            log.info(" [ handle data number: "+resultDataList.size()+" ] ");
        }
        log.info(" [start analysis end ] ");
        return resultDataList;
    }

    private Set<String> parseWordValuesStr(String wordValuesStr,String separator){
        Set<String> wordValueSet = new HashSet<>();
        String[] wordValues = wordValuesStr.split(separator);
        for(String wordValue:wordValues){
            wordValueSet.add(wordValue);
        }
        return wordValueSet;
    }

    private String initTitleBar() {
        String titleBar=" 文件名 ";
//        for(String type: wordValueMap.keySet())
//        {
//            titleBar+=",";
//            titleBar+=type;
//        }
        return titleBar;
    }

    private Map<String ,Integer> countWordValues(List<String> dataList,String wordKey , Set<String> wordValueSet) {

        Map<String ,Integer> valueCountMap = new HashMap<>();

        for(String data:dataList)
        {
            if(LogUtils.containWord(data,wordKey))
            {
                for (String wordValue:wordValueSet)
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
