package cuj.loganalyst.domain;

/**
 * Created by cujamin on 2018/9/20.
 */
public class Record {

    private String callCustomerTime;      //空
    private String sessionId;              //空
    private int resultType;                //空

    public String getCallCustomerTime() {
        return callCustomerTime;
    }

    public void setCallCustomerTime(String callCustomerTime) {
        this.callCustomerTime = callCustomerTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
    }

    @Override
    public String toString() {
        return "Record{" +
                "callCustomerTime='" + callCustomerTime + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", resultType=" + resultType +
                '}';
    }
}