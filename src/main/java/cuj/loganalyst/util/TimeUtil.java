package cuj.loganalyst.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cujamin on 2018/9/20.
 */
public class TimeUtil {
    public static final String TIME_PATTERN = "yyyy-MM-dd hh:mm:ss,SSS";
    public final static DateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN);


    public static long getTimeStamp(String timeStr)
    {
        long timeStamp=0L;
        try {
            timeStamp = timeFormat.parse(timeStr).getTime();
        }catch (ParseException pe)
        {
            System.out.println("时间解析错误");
        }
        return timeStamp;
    }

    public static String getTimeStr(long timeLong)
    {
        return timeFormat.format(new Date(timeLong));
    }
}
