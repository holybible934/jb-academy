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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    private final List<CodeSnippet> codeSnippet = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{id}")
    public ModelAndView getHtml(HttpServletResponse response, @PathVariable int id) {
        response.addHeader("Content-Type", "text/html");
        String code = codeSnippet.get(id - 1).getCode();
        String date = codeSnippet.get(id - 1).getDate();
        ModelAndView model = new ModelAndView("codePage");
        model.addObject("code", code);
        model.addObject("date", date);
        return model;
    }

    @GetMapping(value = "/code/latest")
    public ModelAndView getLatestHtml(HttpServletResponse response) {
        response.addHeader("Content-Type", "text/html");
        ModelAndView model = new ModelAndView("latestCodesPage");
        List<CodeSnippet> sortedCodeSnippets = codeSnippet.stream()
                .sorted(Comparator.comparing(CodeSnippet::getDate))
                .collect(Collectors.toList());
        Collections.reverse(sortedCodeSnippets);
        if (sortedCodeSnippets.size() < 10) {
            model.addObject("snippets", sortedCodeSnippets);
        } else {
            model.addObject("snippets", sortedCodeSnippets.subList(0, 10));
        }
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
        List<CodeSnippet> sortedCodeSnippets = codeSnippet.stream()
                .sorted(Comparator.comparing(CodeSnippet::getDate))
                .collect(Collectors.toList());
        Collections.reverse(sortedCodeSnippets);
        if (sortedCodeSnippets.size() < 10) {
            return sortedCodeSnippets;
        }
        return sortedCodeSnippets.subList(0, 10);
    }

    @PostMapping(value = "/api/code/new")
    public ObjectNode postJson(HttpServletResponse response, @RequestBody ObjectNode code) {
        CodeSnippet newCode = new CodeSnippet(code.get("code").asText());
        codeSnippet.add(newCode);
        ObjectNode node = new ObjectMapper().createObjectNode().put("id", String.valueOf(codeSnippet.lastIndexOf(newCode) + 1));
        response.addHeader("Content-Type", "application/json");
//        System.out.println("POST: Id is " + (codeSnippet.lastIndexOf(newCode) + 1) + ", code is " + codeSnippet.get(codeSnippet.lastIndexOf(newCode)).getCode());
        return node;
    }

    @GetMapping(value = "/api/code/{id}")
    public ObjectNode getCodeWithId(HttpServletResponse response, @PathVariable int id) {
        response.addHeader("Content-Type", "application/json");
//        System.out.println("GET: Id is " + id + ", code is " + codeSnippet.get(id - 1).getCode());
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("code", codeSnippet.get(id - 1).getCode());
        node.put("date", codeSnippet.get(id - 1).getDate());
        return node;
    }

}
