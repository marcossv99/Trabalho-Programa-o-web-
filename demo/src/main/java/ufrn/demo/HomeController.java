package ufrn.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // define o login como o home do sistema
    @GetMapping("/")
    public String home() {
        return "login.html";
    }

}
