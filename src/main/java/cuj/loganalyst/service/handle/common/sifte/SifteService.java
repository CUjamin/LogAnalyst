package cuj.loganalyst.service.handle.common.sifte;

import java.io.File;
import java.util.List;

/**
 * @Auther: cujamin
 * @Date: 2018/10/30 19:33
 * @Description:
 */
public interface SifteService {
    void handle(File file, String toFileName, String charsetName, String containWord);
}
