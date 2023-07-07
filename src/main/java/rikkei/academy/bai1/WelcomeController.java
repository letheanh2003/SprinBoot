package rikkei.academy.bai1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WelcomeController {
    @GetMapping("/welcome/index")
    public ResponseEntity<?> getWelcome(@RequestParam("name") String name) {
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
