package cuj.loganalyst.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cujamin on 2018/4/7.
 */
public class InputServiceImpl implements InputService {
    private final static Logger log = LoggerFactory.getLogger(InputServiceImpl.class);
    private File file = null;
    private BufferedReader reader = null;

    @Override
    public List<String> readFromFile(String fileName) {
        log.info(" [ start to read ... ] ");
        List<String> list = new LinkedList<String>();
        file = new File(fileName);
        try
        {
            reader =new BufferedReader(new FileReader(file));
            String info = reader.readLine();
            while(null!=info)
            {
                list.add(info);
                info = reader.readLine();
            }
        }catch (FileNotFoundException f)
        {
            log.error("没有找到该文件");
        }catch (IOException io)
        {
            log.error(" [ IOException ] ");
        }
        finally {
            log.info(" [ read completed ] ");
            return list;
        }
    }

    public String readNextLine(String fileName)
    {
        String data=null;
        try {
            if (null == file) {
                file = new File(fileName);
            }
            if (null == reader) {
                reader = new BufferedReader(new FileReader(file));
            }
            data=reader.readLine();
        }catch (FileNotFoundException f)
        {
            log.error("没有找到该文件");
        }catch (IOException io)
        {
            log.error(" [ IOException ] ");
        }finally {
            return data;
        }
    }
}
