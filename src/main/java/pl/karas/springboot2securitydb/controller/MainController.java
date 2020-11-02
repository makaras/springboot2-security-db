package pl.karas.springboot2securitydb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.karas.springboot2securitydb.repository.AppUser;
import pl.karas.springboot2securitydb.repository.AppUserRepo;

@Controller
public class MainController {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MainController(AppUserRepo appUserRepo,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
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
    public ModelAndView register(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);
        return new ModelAndView("redirect:/login");
    }

}
