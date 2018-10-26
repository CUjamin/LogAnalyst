package cuj.loganalyst.service.common;

import java.io.File;

/**
 * Created by cujamin on 2018/7/11.
 */
public interface LogAnalysisService {
    void handle(File file, String toFileName, String charsetName, String containWord);
    void handleByLine(File file, String toFileName, String charsetName, String containWord);
}
