package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
        ModelAndView model;
        if (snippet == null || isRestricted(snippet)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            model = new ModelAndView("notFoundPage");
        } else {
            model = new ModelAndView("codePage");
            String code = snippet.getCode();
            String date = snippet.getDate().toString();
            model.addObject("code", code);
            model.addObject("date", date);
            long diff = snippet.getTime().getTime() - Timestamp.valueOf(LocalDateTime.now()).getTime();
            model.addObject("time", diff / 1000);
            model.addObject("views", snippet.getViewLimit() - snippet.getBeenViewed());
        }
        return model;
    }

    private boolean isRestricted(CodeSnippet snippet) {
        return snippet.getViewLimit() < snippet.getBeenViewed()
                || Timestamp.valueOf(LocalDateTime.now()).after(snippet.getTime());
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
        snippet.setViewLimit(code.get("views").asLong(0));
        long secondsDiff = code.get("time").asLong(0);
        if (secondsDiff == 0) {
            snippet.setTime(Timestamp.valueOf(LocalDateTime.MAX));
        } else {
            snippet.setTime(Timestamp.valueOf(LocalDateTime.now().plusSeconds(secondsDiff)));
        }
        repository.save(snippet);
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("id", snippet.getUUId());
        response.addHeader("Content-Type", "application/json");
//        System.out.println("POST: Id is " + snippet.getId() + ", code is " + snippet.getCode());
        return node;
    }

    @GetMapping(value = "/api/code/{uuid}")
    public ObjectNode getCodeWithId(HttpServletResponse response, @PathVariable String uuid) {
        response.addHeader("Content-Type", "application/json");
        CodeSnippet snippet = repository.findByUUId(uuid);
        ObjectNode node = new ObjectMapper().createObjectNode();
        if (snippet == null || isRestricted(snippet)) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
//            System.out.println("GET: Id is " + id + ", code is " + codeSnippet.get(id - 1).getCode());
            snippet.setBeenViewed(snippet.getBeenViewed() + 1);
            snippet = repository.save(snippet);
            node.put("code", snippet.getCode());
            node.put("date", snippet.getDate().toLocalDateTime().toString());
            long diff = snippet.getTime().getTime() - Timestamp.valueOf(LocalDateTime.now()).getTime();
            node.put("time", diff / 1000);
            node.put("views", snippet.getViewLimit() - snippet.getBeenViewed());
        }
        return node;
    }

}
