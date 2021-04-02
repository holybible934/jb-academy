package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    private final CodeSnippetRepository repository;

    @Autowired
    CodeSharingPlatform(CodeSnippetRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{uuid}")
    public ModelAndView getHtml(HttpServletResponse response, @PathVariable String uuid) {
        response.addHeader("Content-Type", "text/html");
        CodeSnippet snippet = repository.findByUUId(uuid);
        String code = snippet.getCode();
        String date = snippet.getDate();
        ModelAndView model = new ModelAndView("codePage");
        model.addObject("code", code);
        model.addObject("date", date);
        return model;
    }

    @GetMapping(value = "/code/latest")
    public ModelAndView getLatestHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("latestCodesPage");
        List<CodeSnippet> sortedCodeSnippets = repository.findTop10ByOrderByIdDesc();
        model.addObject("snippets", sortedCodeSnippets);
        return model;
    }

    @GetMapping(value = "/code/new")
    public ModelAndView getNewCodeHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        return new ModelAndView("newCode");
    }

    @GetMapping(value = "/api/code/latest")
    public List<CodeSnippet> getCodesLatest(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/json");
        return repository.findTop10ByOrderByIdDesc();
    }

    @PostMapping(value = "/api/code/new")
    public ObjectNode postJson(HttpServletResponse response, @RequestBody ObjectNode code) {
        CodeSnippet snippet = new CodeSnippet();
        snippet.setCode(code.get("code").asText());
        snippet.setViewCount(code.get("views").asLong());
        snippet.setTime(code.get("time").asText());
        snippet = repository.save(snippet);
        ObjectNode node = new ObjectMapper().createObjectNode().put("id", snippet.getUUId());
        response.addHeader("Content-Type", "application/json");
//        System.out.println("POST: Id is " + snippet.getId() + ", code is " + snippet.getCode());
        return node;
    }

    @GetMapping(value = "/api/code/{uuid}")
    public ObjectNode getCodeWithId(HttpServletResponse response, @PathVariable String uuid) {
        response.addHeader("Content-Type", "application/json");
        CodeSnippet snippet = repository.findByUUId(uuid);
//        System.out.println("GET: Id is " + id + ", code is " + codeSnippet.get(id - 1).getCode());
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("code", snippet.getCode());
        node.put("date", snippet.getDate());
        return node;
    }

}
