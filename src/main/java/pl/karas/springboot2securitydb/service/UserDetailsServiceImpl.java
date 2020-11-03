package pl.karas.springboot2securitydb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.karas.springboot2securitydb.repository.AppUserRepo;
import pl.karas.springboot2securitydb.repository.VerificationTokenRepo;
import pl.karas.springboot2securitydb.repository.entity.AppUser;
import pl.karas.springboot2securitydb.repository.entity.VerificationToken;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepo appUserRepo;
    private VerificationTokenRepo verificationTokenRepo;
    private PasswordEncoder passwordEncoder;
    private MailSenderService mailSenderService;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepo appUserRepo,
                                  VerificationTokenRepo verificationTokenRepo,
                                  PasswordEncoder passwordEncoder,
                                  MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.verificationTokenRepo = verificationTokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return appUserRepo.findAllByUsername(name);
    }

    public void addNewUser(AppUser user, HttpServletRequest request) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        appUserRepo.save(user);

        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepo.save(verificationToken);

        String url = String.format(
                "http://%s:%s%s/verify-token?token=%s", request.getServerName(), request.getServerPort(),
                request.getContextPath(), token);

        try {
            mailSenderService.sendMail(user.getUsername(), "Verification Token", url, false);
        } catch (MessagingException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    public void verifyToken(String token) {
        ofNullable(verificationTokenRepo.findByValue(token))
                .map(VerificationToken::getAppUser)
                .ifPresent(appUser -> {
                    appUser.setEnabled(true);
                    appUserRepo.save(appUser);
                });
    }
}
