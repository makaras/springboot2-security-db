package pl.karas.springboot2securitydb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import pl.karas.springboot2securitydb.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final int ONE_DAY_IN_SECONDS = 86400;
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/unknown").permitAll()
                .antMatchers("/goodbye").permitAll()
                .antMatchers("/sign-up").permitAll()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/user").permitAll()
                .and().logout().logoutSuccessUrl("/goodbye")
                .and().rememberMe().tokenValiditySeconds(ONE_DAY_IN_SECONDS)
                .rememberMeCookieName("refresh").rememberMeParameter("remember");
    }

}
