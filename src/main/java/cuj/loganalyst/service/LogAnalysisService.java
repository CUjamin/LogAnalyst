package cuj.loganalyst.service;

import cuj.loganalyst.service.handle.common.Handler;
import cuj.loganalyst.service.io.input.InputService;
import cuj.loganalyst.service.io.output.OutputService;

import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public interface LogAnalysisService {
//    void handle(String path, String toFileName, String charsetName,String containWord, Map<String,Object> params);


    void handle(String path, String toFileName, String charsetName,Map<String, Object> params);
}
