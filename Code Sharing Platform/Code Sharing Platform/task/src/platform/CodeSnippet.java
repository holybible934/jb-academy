package platform;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "snippets")
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String code;
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

    public int getId() {
        return id;
    }

}