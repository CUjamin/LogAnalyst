package cuj.loganalyst.service;

/**
 * Created by cujamin on 2018/9/20.
 */
public interface RecordAnalysisService {
    void handle(String fromFileName,String toFileName,String charsetName,String containWord,int resultType);
}
