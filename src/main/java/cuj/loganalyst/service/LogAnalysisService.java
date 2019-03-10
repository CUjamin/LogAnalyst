package cuj.loganalyst.service;

import java.util.Map;

/**
 * Created by cujamin on 2018/7/11.
 */
public interface LogAnalysisService {
    /**
     *
     * @param path              原始文件路径
     * @param toFileName        结果文件名称
     * @param charsetName       结果数据编码
     * @param params            处理参数
     */
    void handle(String path, String toFileName, String charsetName,Map<String, Object> params);
}
