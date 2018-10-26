package cuj.loganalyst.service.common.marge;

import java.io.File;

/**
 * @Auther: cujamin
 * @Date: 2018/10/26 13:13
 * @Description:
 */
public interface MergeService {
    void handle(File fileA, File fileB, String toFileName, String charsetName);
}
