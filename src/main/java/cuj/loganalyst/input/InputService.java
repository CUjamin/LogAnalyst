package cuj.loganalyst.input;

import java.util.List;

/**
 * Created by cujamin on 2018/4/7.
 */
public interface InputService {
    List<String> readFromFile(String fileName);
    String readNextLine(String fileName);
}
