package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    private Code code = new Code("System.out.println(\"Hello World\");");
    private final List<Code> codeSnippet = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{id}")
    public ModelAndView getHtml(HttpServletResponse response, @PathVariable int id) {
        response.addHeader("Content-Type", "text/html");
        String code = codeSnippet.get(id).getCode();
        String date = codeSnippet.get(id).getDate();

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
        this.code = new Code(code.getCode());
        response.addHeader("Content-Type", "application/json");
        return code;
    }

    @PostMapping(value = "/api/code/new")
    public ObjectNode postJson(HttpServletResponse response, @RequestBody ObjectNode code) {
        this.code = new Code(code.get("code").asText());
        codeSnippet.add(this.code);
        ObjectNode node = new ObjectMapper().createObjectNode().put("id", codeSnippet.lastIndexOf(this.code));
        response.addHeader("Content-Type", "application/json");
        return node;
    }

    @GetMapping(value = "/api/code/{id}")
    public Code getJson(HttpServletResponse response, @PathVariable int id) {
        response.addHeader("Content-Type", "application/json");
        if (id > 0) {
            return codeSnippet.get(id - 1);
        } else {
            return null;
        }
    }

}
