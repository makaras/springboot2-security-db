package pl.karas.springboot2securitydb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.karas.springboot2securitydb.repository.entity.AppUser;
import pl.karas.springboot2securitydb.service.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public MainController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/sign-up")
    public ModelAndView signUp() {
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser user, HttpServletRequest request) {
        userDetailsService.addNewUser(user, request);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView verifyToken(@RequestParam String token) {
        userDetailsService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

}
