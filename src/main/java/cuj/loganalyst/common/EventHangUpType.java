package cuj.loganalyst.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cujamin on 2018/9/21.
 */
public class EventHangUpType {
    public static final String CALL_REJECTED="CALL_REJECTED";
    public static final String INCOMPATIBLE_DESTINATION="INCOMPATIBLE_DESTINATION";
    public static final String USER_BUSY="USER_BUSY";
    public static final String NO_ANSWER="NO_ANSWER";
    public static final String RECOVERY_ON_TIMER_EXPIRE="RECOVERY_ON_TIMER_EXPIRE";
    public static final String NORMAL_TEMPORARY_FAILURE="NORMAL_TEMPORARY_FAILURE";
    public static final String NORMAL_CLEARING="NORMAL_CLEARING";
    public static final String ORIGINATOR_CANCEL="ORIGINATOR_CANCEL";
    public static final String TOTLE="TOTLE";
    public static final Map<String ,Integer> EVENT_HANGUP_S=new HashMap<>();
    static {
        EVENT_HANGUP_S.put(EventHangUpType.CALL_REJECTED,0);
        EVENT_HANGUP_S.put(EventHangUpType.INCOMPATIBLE_DESTINATION,1);
        EVENT_HANGUP_S.put(EventHangUpType.USER_BUSY,2);
        EVENT_HANGUP_S.put(EventHangUpType.NO_ANSWER,3);
        EVENT_HANGUP_S.put(EventHangUpType.RECOVERY_ON_TIMER_EXPIRE,4);
        EVENT_HANGUP_S.put(EventHangUpType.NORMAL_TEMPORARY_FAILURE,5);
        EVENT_HANGUP_S.put(EventHangUpType.NORMAL_CLEARING,6);
        EVENT_HANGUP_S.put(EventHangUpType.ORIGINATOR_CANCEL,7);
        EVENT_HANGUP_S.put(EventHangUpType.TOTLE,8);
    }
}
