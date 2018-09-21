package cuj.loganalyst.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cujamin on 2018/9/20.
 */
public class TimeUtilTest {

    @Test
    public void getTimeStr() throws Exception {
        long timeLong = 1537242257682L;
        System.out.println(TimeUtil.getTimeStr(timeLong));
    }

    @Test
    public void getTimeStamp() throws Exception {
        System.out.println(TimeUtil.getTimeStamp("2018-09-18 11:44:17,682"));
    }

}