package cuj.loganalyst.domain;

import java.util.Map;

/**
 * @Auther: cujamin
 * @Date: 2019/3/1 14:45
 * @Description:
 */
public class LogVo {
    private String flogWord;

    private Map<String,String > KeyValueMap;

    public String getFlogWord() {
        return flogWord;
    }

    public void setFlogWord(String flogWord) {
        this.flogWord = flogWord;
    }

    public Map<String, String> getKeyValueMap() {
        return KeyValueMap;
    }

    public void setKeyValueMap(Map<String, String> keyValueMap) {
        KeyValueMap = keyValueMap;
    }

    @Override
    public String toString() {
        return "LogVo{" +
                "flogWord='" + flogWord + '\'' +
                ", KeyValueMap=" + KeyValueMap +
                '}';
    }
}
