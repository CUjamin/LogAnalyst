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

        String timeStrB = "2018-10-31 11:59:50,065";
        System.out.println(timeStrB.replace(",","."));
        System.out.println(TimeUtil.getTimeStamp("2018-09-18 11:44:17,682"));
        System.out.println("2018-10-31 11:59:50,065:"+TimeUtil.getTimeStamp("2018-10-31 11:59:50,065"));
        System.out.println("2018-10-31 12:00:10,122:"+TimeUtil.getTimeStamp("2018-10-31 12:00:10,122"));
    }

}