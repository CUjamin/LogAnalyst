package cuj.loganalyst.util;

import com.alibaba.fastjson.JSON;
import cuj.loganalyst.domain.Record;

/**
 * Created by cujamin on 2018/9/20.
 */
public class RecordUtil {
    public static Record getRecord(String logstr)
    {
        String spit1 = "callResult : ";
        String spit2 = " ]";
        String tempStr = logstr.split(spit1)[1].split(spit2)[0];
        return JSON.parseObject(tempStr , Record.class);
    }
}
