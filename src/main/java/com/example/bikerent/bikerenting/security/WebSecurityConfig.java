package com.example.bikerent.bikerenting.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private static final String USER = "USER";



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.authorizeRequests().antMatchers("/rental/all").hasRole(ADMIN)
                .antMatchers("/user/auth").hasAnyRole(ADMIN,USER)
                .antMatchers("/rental/byUser").hasAnyRole(USER)
                .antMatchers("/rental/singleUserRental/**").hasAnyRole(USER)
                .antMatchers("/rental/rentBike/**").hasAnyRole(USER)
                .antMatchers("/rental/returnBike/**").hasAnyRole(USER)
                .antMatchers("/user/all").hasRole(ADMIN)
                .antMatchers("/rest/**").fullyAuthenticated()
                .antMatchers("/h2-console/**").fullyAuthenticated()
                .antMatchers("/user/add").permitAll()
                .antMatchers("/bikes/all").permitAll()

                .anyRequest().fullyAuthenticated();
        http.formLogin()
                .usernameParameter("name")
                .passwordParameter("pwd")
                .loginPage("/api/login").permitAll();

        http.logout().logoutUrl("/api/logout").permitAll();

        http.csrf().disable();

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }
    }


    @Bean
    public PasswordEncoder encodePWD(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}