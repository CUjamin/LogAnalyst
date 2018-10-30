package cuj.loganalyst.service;

import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public interface LogAnalysisService {
    void handle(String path, String toFileName, String charsetName,String containWord, Map<String,Object> params);
}
