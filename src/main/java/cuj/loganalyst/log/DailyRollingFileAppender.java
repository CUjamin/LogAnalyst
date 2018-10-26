package cuj.loganalyst.log;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.QuietWriter;

import java.io.File;
import java.io.IOException;

/**
 * Created by cujamin on 2018/9/25.
 */
public class DailyRollingFileAppender extends org.apache.log4j.DailyRollingFileAppender {
    public DailyRollingFileAppender() {
        super();
        Runtime.getRuntime().addShutdownHook(new Log4jHockThread());
    }

    public DailyRollingFileAppender(Layout layout, String filename, String datePattern) throws IOException
    {
        super(layout, filename, datePattern);
        Runtime.getRuntime().addShutdownHook(new Log4jHockThread());
    }

    @Override
    public synchronized void setFile(String fileName, boolean append,
                                     boolean bufferedIO, int bufferSize) throws IOException {
        File logfile = new File(fileName);         logfile.getParentFile().mkdirs();
        super.setFile(fileName, append, bufferedIO, bufferSize);
    }

    public QuietWriter getQw() {
        return super.qw;
    }

    private class Log4jHockThread extends Thread {
        @Override
        public void run() {
            QuietWriter qw = DailyRollingFileAppender.this.getQw();
            if (qw != null) {
                qw.flush();
            }
        }
    }
}

