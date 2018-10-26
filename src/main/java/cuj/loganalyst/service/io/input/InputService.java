package cuj.loganalyst.service.io.input;

import java.io.File;
import java.util.List;

/**
 * Created by cujamin on 2018/4/7.
 */
public interface InputService {
    List<String> readFromFile(File file);
    String readNextLine(File file);
}
