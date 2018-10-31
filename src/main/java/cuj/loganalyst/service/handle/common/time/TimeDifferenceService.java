package cuj.loganalyst.service.handle.common.time;

import java.io.File;

/**
 * @Auther: cujamin
 * @Date: 2018/10/31 11:52
 * @Description:
 */
public interface TimeDifferenceService {
    void handle(File file, String toFileName, String charsetName, String containWord);
}
