package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Code {

    private String code;
    private String date;

    Code(String code) {
        this.code = code;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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