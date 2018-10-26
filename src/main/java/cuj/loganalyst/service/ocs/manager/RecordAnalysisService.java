package cuj.loganalyst.service.ocs.manager;

import java.io.File;

/**
 * Created by cujamin on 2018/9/20.
 */
public interface RecordAnalysisService {
    void handle(File file, String toFileName, String charsetName, String containWord, int resultType);
}
