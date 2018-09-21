package cuj.loganalyst.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cujamin on 2018/9/20.
 */
public class RecordUtilTest {
    @Test
    public void getRecord() throws Exception {
        String logStr = "2018-09-18 11:44:45,965 INFO[pool-5-thread-21](MessageRecorder.java241)-013979905598 : [ ocs_call_list_results表保存成功！callResult : {\"attemptTimes\":1,\"callCustomerTime\":\"1537242260088\",\"customerId\":\"75BFE308A6C41740E053C304100B7730\",\"customerPhoneNumber\":\"013979905598\",\"resultType\":2,\"sessionId\":\"bce72207-f591-49a8-bace-df78aa164027\",\"taskId\":\"75BB6AB66DE93B3BE053C304100B8642\",\"tenantId\":\"ycdx\"} ]\n";
        System.out.println(RecordUtil.getRecord(logStr).toString());

    }
}