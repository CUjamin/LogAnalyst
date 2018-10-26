package cuj.loganalyst.service.io.output;

import java.util.List;

/**
 * Created by cujamin on 2018/4/6.
 */
public interface OutputService {
    void outputInFile(List<String> list, String fileName, String charsetName);
    void writeNextLine(String data, String fileName, String charsetName);
}
