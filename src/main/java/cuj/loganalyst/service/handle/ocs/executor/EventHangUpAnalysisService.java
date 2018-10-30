package cuj.loganalyst.service.handle.ocs.executor;

import java.io.File;

/**
 * Created by cujamin on 2018/9/21.
 */
public interface EventHangUpAnalysisService {
    void handle(File[] files, String toFileName, String charsetName, String containWord);
}
