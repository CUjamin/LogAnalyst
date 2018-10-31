package cuj.loganalyst.service.handle.common.sifte;

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
 * @Auther: cujamin
 * @Date: 2018/10/30 18:24
 * @Description:
 */
public class SifteServiceImpl implements SifteService {
    private final static Logger log = LoggerFactory.getLogger(SifteServiceImpl.class);

    private InputService inputService = new InputServiceImpl();
    private OutputService outputService = new OutputServiceImpl();
    @Override
    public void handle(File file, String toFileName, String charsetName, String containWord)
    {
        log.info(" [start analysis ... ] ");
        List<String> dataList = inputService.readFromFile(file);
        List<String> tempList = new ArrayList<String>();
        if(null!=dataList&&dataList.size()>0)
        {
            for(String tempStr:dataList)
            {
                if(LogUtils.containWord(tempStr, containWord))
                {
                    tempList.add(handleWord(tempStr, containWord));
                }
            }
            log.info(" [ handle data number: "+tempList.size()+" ] ");
        }
        log.info(" [start analysis end ] ");
        outputService.outputInFile(tempList, String.format("%s.log", toFileName),charsetName);
    }

    private String handleWord(String data,String word)
    {
//        word= String.format("ycdx_%s_", word);
//        data = data.replaceAll(word,"");
        return data;
    }
}
