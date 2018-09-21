package cuj.loganalyst.service;

import cuj.loganalyst.input.InputService;
import cuj.loganalyst.input.InputServiceImpl;
import cuj.loganalyst.output.OutputService;
import cuj.loganalyst.output.OutputServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalysisServiceImpl implements LogAnalysisService {
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
            List<String> tempList = new ArrayList<String>();
            for(String tempStr:dataList)
            {
                if(containWord(tempStr, containWord))
                {
                    tempList.add(handleWord(tempStr, containWord));
                }
            }
            log.info(" [ handle data number: "+tempList.size()+" ] ");
            outputService.outputInFile(tempList,toFileName,charsetName);
        }
        log.info(" [start analysis end ] ");
    }

    @Override
    public void handleByLine(String fromFileName,String toFileName,String charsetName,String word) {
        String tempStr = inputService.readNextLine(fromFileName);
        while(null!=tempStr)
        {
            if(containWord(tempStr,word))
            {
                outputService.writeNextLine(handleWord(tempStr,word),toFileName,charsetName);
            }
            tempStr = inputService.readNextLine(fromFileName);
        }
    }


    private boolean containWord(String data,String word)
    {
        String pattern = String.format(".*%s.*", word);
        return Pattern.matches(pattern, data);
    }
    private String handleWord(String data,String word)
    {
//        word= String.format("ycdx_%s_", word);
//        data = data.replaceAll(word,"");
        return data;
    }
}
