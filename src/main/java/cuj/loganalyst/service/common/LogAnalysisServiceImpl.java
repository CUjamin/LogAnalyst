package cuj.loganalyst.service.common;

import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.input.InputServiceImpl;
import cuj.loganalyst.service.io.output.OutputService;
import cuj.loganalyst.service.io.output.OutputServiceImpl;
import cuj.loganalyst.util.LogUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cujamin on 2018/7/11.
 */
public class LogAnalysisServiceImpl implements LogAnalysisService {
    private final static Logger log = LoggerFactory.getLogger(LogAnalysisServiceImpl.class);
    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();

    @Override
    public void handle(File file, String toFileName, String charsetName, String containWord)
    {
        log.info(" [start analysis ... ] ");
        List<String> dataList = inputService.readFromFile(file);
        if(null!=dataList&&dataList.size()>0)
        {
            List<String> tempList = new ArrayList<String>();
            for(String tempStr:dataList)
            {
                if(LogUtils.containWord(tempStr, containWord))
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
    public void handleByLine(File file,String toFileName,String charsetName,String word) {
        String tempStr = inputService.readNextLine(file);
        while(null!=tempStr)
        {
            if(LogUtils.containWord(tempStr,word))
            {
                outputService.writeNextLine(handleWord(tempStr,word),toFileName,charsetName);
            }
            tempStr = inputService.readNextLine(file);
        }
    }

    private String handleWord(String data,String word)
    {
//        word= String.format("ycdx_%s_", word);
//        data = data.replaceAll(word,"");
        return data;
    }
}
