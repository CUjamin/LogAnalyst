package cuj.loganalyst.util;

import org.apache.log4j.helpers.DateTimeDateFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cujamin on 2018/9/20.
 */
public class TimeUtil {
    public static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";
    public final static DateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN);


    public static long getTimeStamp(String timeStr)throws ParseException
    {
        long timeStamp=0L;
        timeStamp = timeFormat.parse(timeStr).getTime();
        return timeStamp;
    }

    public static long getTimeStamp(String timeStr,String timePattern)throws ParseException
    {
        long timeStamp=0L;
        DateFormat format = new SimpleDateFormat(timePattern);
        timeStamp = format.parse(timeStr).getTime();
        return timeStamp;
    }

    public static String getTimeStr(long timeLong)
    {
        return timeFormat.format(new Date(timeLong));
    }

    public static String getTimeStr(long timeLong,String timePattern)
    {
        DateFormat format = new SimpleDateFormat(timePattern);
        return format.format(new Date(timeLong));
    }
    public static long difTime(String timeStrA,String timeStrB)throws ParseException{
        return  timeFormat.parse(timeStrB).getTime()-timeFormat.parse(timeStrA).getTime();
    }
}
