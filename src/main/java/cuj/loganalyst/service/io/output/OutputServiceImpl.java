package cuj.loganalyst.service.io.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by cujamin on 2018/4/6.
 */
public class OutputServiceImpl implements OutputService {
    private final static Logger log = LoggerFactory.getLogger(OutputServiceImpl.class);

    /**
     * output result in file
     * @param list
     */
    @Override
    public void outputInFile(List<String> list,String fileName,String charsetName) {
        OutputStreamWriter writer = null;
        FileOutputStream fout = null;

        File file=new File(fileName);
        if(!file.exists())
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            log.info(" [ start to write ... ] ");
            fout = new FileOutputStream(file);
            writer = new OutputStreamWriter(fout,charsetName);

            for(String data:list)
            {
                writer.append(data);
                writer.append("\r\n");
            }
            writer.close();
            fout.close();
        }catch (IOException io)
        {
            log.error(" [ IOException ERROR :{}] ",io);
        }finally {
            writer = null;
            fout = null;
            log.info(" [ write completed ,filename:"+fileName+"] ");
        }
    }

    public void writeNextLine(String data,String fileName,String charsetName)
    {
        OutputStreamWriter writer = null;
        FileOutputStream fout = null;
        try
        {
            fout = new FileOutputStream(new File(fileName));
            writer = new OutputStreamWriter(fout,charsetName);
            writer.append(data);
            writer.append("\r\n");
            writer.close();
            fout.close();
        }catch (IOException io)
        {
            log.error(" [ IOException ERROR :{}] ",io);
        }finally {
            writer = null;
            fout = null;
        }
    }
}
