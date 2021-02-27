package calculator;

import org.springframework.web.bind.annotation.*;

@RestController
class ArithmeticRestController {

    @GetMapping(path = "/{operation}")
    @ResponseBody
    public String calculate(@PathVariable String operation, @RequestParam int a, @RequestParam int b) {
        switch (operation) {
            case "add":
                return String.valueOf(a + b);
            case "subtract":
                return String.valueOf(a - b);
            case "mult":
                return String.valueOf(a * b);
            default:
                return "Unknown operation";
        }
    }
}