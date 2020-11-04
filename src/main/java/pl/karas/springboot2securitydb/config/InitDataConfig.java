package pl.karas.springboot2securitydb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.karas.springboot2securitydb.repository.AppUserRepo;
import pl.karas.springboot2securitydb.repository.entity.AppUser;

@Configuration
public class InitDataConfig {

    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InitDataConfig(AppUserRepo appUserRepo,
                          PasswordEncoder passwordEncoder) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        initData();
    }

    private void initData() {
        AppUser appUser = new AppUser();
        appUser.setUsername("Maciek");
        appUser.setEnabled(true);
        appUser.setPassword(passwordEncoder.encode("123"));
        appUserRepo.save(appUser);
    }
}
