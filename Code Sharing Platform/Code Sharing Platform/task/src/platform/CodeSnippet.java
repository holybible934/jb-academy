package platform;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @JsonIgnore
    private long id;

    @Column(name = "code", columnDefinition = "TEXT", nullable = false)
    private String code;

    @Column(name = "date", nullable = false)
    private String date;

    CodeSnippet(String code) {
        this.code = code;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.date = LocalDateTime.now().format(formatter);
    }

    public String getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public Long getId() {
        return id;
    }

}