package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    private Code code = new Code("System.out.println(\"Hello World\");");

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code")
    public ModelAndView getHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        String codeBody = code.getCode();
        LocalDateTime dateTime = code.getDateTime();

        ModelAndView model = new ModelAndView("codePage");
        model.addObject("codeBody", codeBody);
        model.addObject("dateTime", dateTime.toString());
        return model;

    }

    @GetMapping(value = "/api/code")
    public Code getJson(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        return code;
    }

}
