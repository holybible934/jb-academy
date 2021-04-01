package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CodeSnippet {

    @Id
    @JsonIgnore
    @Column(name = "uuid", columnDefinition = "TEXT", nullable = false)
    private String id;

    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "view_count")
    private long viewCount;

    @Column(name = "viewed_count")
    private long viewedCount;

    @Column(name = "time")
    private String time;

    public CodeSnippet() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getUUId() {
        return id;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getViewedCount() {
        return viewedCount;
    }

    public void setViewedCount(long viewedCount) {
        this.viewedCount = viewedCount;
    }
}