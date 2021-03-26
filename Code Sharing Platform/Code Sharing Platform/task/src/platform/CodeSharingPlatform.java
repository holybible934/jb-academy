package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Controller
public class CodeSharingPlatform {

    //private final List<CodeSnippet> codeSnippet = new ArrayList<>();
    private final CodeSnippetRepository repository;

    @Autowired
    CodeSharingPlatform(CodeSnippetRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(value = "/code/{id}")
    public ModelAndView getHtml(HttpServletResponse response, @PathVariable long id) {
        response.addHeader("Content-Type", "text/html");
        CodeSnippet snippet = repository.findById(id);
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
        List<CodeSnippet> sortedCodeSnippets = repository.findTop10ByOrderByDateDesc();
//        List<CodeSnippet> sortedCodeSnippets = codeSnippet.stream()
//                .sorted(Comparator.comparing(CodeSnippet::getDate))
//                .collect(Collectors.toList());
//        Collections.reverse(sortedCodeSnippets);
//        if (sortedCodeSnippets.size() < 10) {
//            model.addObject("snippets", sortedCodeSnippets);
//        } else {
//            model.addObject("snippets", sortedCodeSnippets.subList(0, 10));
//        }
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
        List<CodeSnippet> sortedCodeSnippets = repository.findTop10ByOrderByDateDesc();
//        List<CodeSnippet> sortedCodeSnippets = codeSnippet.stream()
//                .sorted(Comparator.comparing(CodeSnippet::getDate))
//                .collect(Collectors.toList());
//        Collections.reverse(sortedCodeSnippets);
//        if (sortedCodeSnippets.size() < 10) {
//            return sortedCodeSnippets;
//        }
        return sortedCodeSnippets;
    }

    @PostMapping(value = "/api/code/new")
    public ObjectNode postJson(HttpServletResponse response, @RequestBody ObjectNode code) {
        CodeSnippet newCode = new CodeSnippet(code.get("code").asText());
        CodeSnippet snippet = repository.save(newCode);
        //codeSnippet.add(newCode);
        ObjectNode node = new ObjectMapper().createObjectNode().put("id", String.valueOf(snippet.getId()));
        response.addHeader("Content-Type", "application/json");
//        System.out.println("POST: Id is " + (codeSnippet.lastIndexOf(newCode) + 1) + ", code is " + codeSnippet.get(codeSnippet.lastIndexOf(newCode)).getCode());
        return node;
    }

    @GetMapping(value = "/api/code/{id}")
    public ObjectNode getCodeWithId(HttpServletResponse response, @PathVariable long id) {
        response.addHeader("Content-Type", "application/json");
        CodeSnippet snippet = repository.findById(id);
//        System.out.println("GET: Id is " + id + ", code is " + codeSnippet.get(id - 1).getCode());
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("code", snippet.getCode());
        node.put("date", snippet.getDate());
        return node;
    }

}
