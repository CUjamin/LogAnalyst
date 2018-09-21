package cuj.loganalyst.service;

/**
 * Created by cujamin on 2018/9/21.
 */
public interface EventHangUpAnalysisService {
    void handle(String fromFileName,String toFileName,String charsetName,String containWord);
}
