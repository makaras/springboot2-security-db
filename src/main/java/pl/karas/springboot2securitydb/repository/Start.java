package pl.karas.springboot2securitydb.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Start {

    @Autowired
    public Start(PasswordEncoder passwordEncoder,
                 AppUserRepo appUserRepo) {

        AppUser appUser = new AppUser();
        appUser.setUsername("Maciek");
        appUser.setPassword(passwordEncoder.encode("Maciek123"));
        appUserRepo.save(appUser);
    }
}
