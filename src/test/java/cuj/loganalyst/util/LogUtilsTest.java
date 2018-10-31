package cuj.loganalyst.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Auther: cujamin
 * @Date: 2018/10/31 13:41
 * @Description:
 */
public class LogUtilsTest {

    @Test
    public void getPatternOrder() {
        System.out.println(LogUtils.getPatternOrder("a,b,c,d"));
        System.out.println(LogUtils.getPatternOrder("a"));
    }

    @Test
    public void containWordOrder() {

    }

    @Test
    public void containWordDisorder() {

    }
}