package platform;

import java.sql.Timestamp;

public class SnippetListEntity {
    private final String code;
    private final String date;
    private long time;
    private long views;

    public SnippetListEntity(String code, String date, long time, long views) {
        this.code = code;
        this.date = date;
        this.time = time;
        this.views = views;
    }

    public String getCode() {
        return code;
    }

    public String getDate() {
        return date;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
