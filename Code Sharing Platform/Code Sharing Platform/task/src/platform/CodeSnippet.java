package platform;

import javax.persistence.Entity;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(name = "snippets")
public class CodeSnippet {

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

    public void setCode(String code) {
        this.code = code;
    }
}