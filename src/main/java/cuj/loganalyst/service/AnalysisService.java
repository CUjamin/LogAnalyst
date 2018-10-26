package cuj.loganalyst.service;

import java.io.File;

/**
 * @Auther: cujamin
 * @Date: 2018/10/25 16:38
 * @Description:
 */
public interface AnalysisService {
    void handle(File[] files, String toFileName, String charsetName, String containWord);
}
