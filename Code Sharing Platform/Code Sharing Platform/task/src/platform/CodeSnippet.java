package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "uuid", columnDefinition = "TEXT", nullable = false)
    private String UUId;

    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Column(name = "view_limit")
    private long viewLimit;

    @Column(name = "been_viewed")
    private long beenViewed;

    @Column(name = "time")
    private Timestamp time;

    public CodeSnippet() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        //this.date = new Timestamp()LocalDateTime.now().format(formatter);
        this.UUId = UUID.randomUUID().toString();
        this.date = Timestamp.valueOf(LocalDateTime.now());
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getUUId() {
        return UUId;
    }

    public long getViewLimit() {
        return viewLimit;
    }

    public void setViewLimit(long viewLimit) {
        this.viewLimit = viewLimit;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public long getBeenViewed() {
        return beenViewed;
    }

    public void setBeenViewed(long beenViewed) {
        this.beenViewed = beenViewed;
    }
}