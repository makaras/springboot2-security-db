package pl.karas.springboot2securitydb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    private static final String HELLO_MSG = "Hello %s";

    @GetMapping("/admin")
    public String forAdmin(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "admin");
    }

    @GetMapping("/user")
    public String forUser(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "user ");
    }

    @GetMapping("/unknown")
    public String forAll(Principal principal) {
        String name = principal.getName();
        return String.format(HELLO_MSG, "unknown");
    }

    @GetMapping("/goodbye")
    public String goodbye() {
        return "goodbye";
    }
}
