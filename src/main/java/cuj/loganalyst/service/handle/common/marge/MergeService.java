package cuj.loganalyst.service.handle.common.marge;

import java.io.File;

/**
 * @Auther: cujamin
 * @Date: 2018/10/30 19:31
 * @Description:
 */
public interface MergeService {
    void handle(File[] files, String toFileName, String charsetName);
}
