package cuj.loganalyst.service.handle.common;

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
     * @param dataLists 原始数据
     * @param params    处理参数
     * @return
     */
    List<String> handle(List<List<String>> dataLists,Map<String, Object> params);
}
