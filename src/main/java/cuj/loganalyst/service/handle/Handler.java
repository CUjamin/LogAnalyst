package cuj.loganalyst.service.handle;

import cuj.loganalyst.service.io.output.OutputService;

import java.util.List;
import java.util.Map;

/**
 * @Auther: cujamin
 * @Date: 2019/3/1 14:52
 * @Description:
 */
public interface Handler {

    /**
     *
     * @param dataListMap   原始数据    Map < fileName,List< dataStr > >
     * @param params        处理参数
     * @param outputService
     */
    void handle(
            Map<String,List<String>> dataListMap,
            Map<String, Object> params,
            OutputService outputService);
}
