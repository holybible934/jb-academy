package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        String code = this.code.getCode();
        String date = this.code.getDate();

        ModelAndView model = new ModelAndView("codePage");
        model.addObject("code", code);
        model.addObject("date", date);
        return model;
    }

    @GetMapping(value = "/code/new")
    public ModelAndView getNewCodeHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("newCode");
        return new ModelAndView("newCode");
    }

    @GetMapping(value = "/api/code")
    public Code getJson(HttpServletResponse response) {
        code = new Code(code.getCode());
        response.addHeader("Content-Type", "application/json");
        return code;
    }

    @PostMapping(value = "/api/code/new")
    public Code postJson(HttpServletResponse response, @RequestParam Code newCode) {
        this.code = new Code(newCode.getCode());
        response.addHeader("Content-Type", "application/json");
        return code;
    }

}