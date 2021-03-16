package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

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

    @GetMapping(value = "/code/latest")
    public ModelAndView getLatestHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("latestCodesPage");
        model.addObject("snippets", codeSnippet);
        return model;
    }

    @GetMapping(value = "/code/new")
    public ModelAndView getNewCodeHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("newCode");
        return new ModelAndView("newCode");
    }

    @GetMapping(value = "/api/code/latest")
    public List<Code> getCodesLatest(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        if (codeSnippet.size() < 10) {
            return codeSnippet;
        }
        return codeSnippet.subList(codeSnippet.size() - 10, codeSnippet.size());
    }

    @PostMapping(value = "/api/code/new")
    public ObjectNode postJson(HttpServletResponse response, @RequestBody ObjectNode code) {
        Code newCode = new Code(code.get("code").asText());
        codeSnippet.add(newCode);
        Collections.reverse(codeSnippet);
        ObjectNode node = new ObjectMapper().createObjectNode().put("id", String.valueOf(codeSnippet.lastIndexOf(newCode)));
        response.addHeader("Content-Type", "application/json");
        return node;
    }

    @GetMapping(value = "/api/code/{id}")
    public Code getCodeWithId(HttpServletResponse response, @PathVariable int id) {
        response.addHeader("Content-Type", "application/json");
        if ((id > 0) && (id < codeSnippet.size())) {
            return codeSnippet.get(id - 1);
        } else {
            return new Code("public static void ...");
        }
    }

}
