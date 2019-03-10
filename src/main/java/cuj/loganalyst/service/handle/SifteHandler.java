package cuj.loganalyst.service.handle;

import cuj.loganalyst.common.ConfigKey;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

/**
 * @Auther: cujamin
 * @Date: 2019/3/7 15:39
 * @Description:
 */
public class SifteHandler implements Handler{
    private final static Logger log = LoggerFactory.getLogger(SifteHandler.class);
    private final static String spitKey1 = " INFO";

    public void handle(
            Map<String,List<String>> dataListMap,
            Map<String, Object> params,
            OutputService outputService){

        log.info(" [start analysis ... ] ");
        Iterator<Map.Entry<String, List<String>>> it = dataListMap.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, List<String>> entry = it.next();
            List<String> dataList = entry.getValue();
            List<String> tempList = new ArrayList<String>();
            if(null!=dataList&&dataList.size()>0)
            {
                for(String tempStr:dataList)
                {
                    if(LogUtils.containWord(tempStr, (String)params.get(ConfigKey.WORD_KEY)))
                    {
                        tempList.add(handleWord(tempStr, (String)params.get(ConfigKey.WORD_KEY)));
                    }
                }
                log.info(" [ handle data number: "+tempList.size()+" ] ");
            }

            String suffix = (String)params.get(ConfigKey.SUFFIX);
            String filename = entry.getKey()+"."+suffix;
            outputService.outputInFile(tempList, filename,(String)params.get(ConfigKey.CHAR_SET));
        }
        log.info(" [start analysis end ] ");

    }

    private String handleWord(String data,String word)
    {
//        word= String.format("ycdx_%s_", word);
//        data = data.replaceAll(word,"");
        return data;
    }
}
