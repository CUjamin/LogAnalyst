package cuj.loganalyst.service;

/**
 * Created by cujamin on 2018/7/11.
 */
public interface LogAnalysisService {
    void handle(String fromFileName,String toFileName,String charsetName,String containWord);
    void handleByLine(String fromFileName,String toFileName,String charsetName,String containWord);
}
