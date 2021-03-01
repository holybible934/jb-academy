package platform;

import java.time.LocalDateTime;

public class Code {

    private String code;
    private LocalDateTime dateTime;

    Code(String code) {
        this.code = code;
        this.dateTime = LocalDateTime.now();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}