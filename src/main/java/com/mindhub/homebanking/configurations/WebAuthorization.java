package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/manager.html").hasAuthority("ADMIN")
                .antMatchers("/createloan.html").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients").hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.GET, "/api/clients/{id}").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/clients/current").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html").hasAuthority("CLIENT")
                .antMatchers("/web/account.html").hasAuthority("CLIENT")
                .antMatchers("/web/cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/loan-application.html").hasAuthority("CLIENT")
                .antMatchers("/web/create-cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/transfers.html").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/account").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST,"/api/client/transaction/debit").permitAll()
                .antMatchers(HttpMethod.POST,"/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST,"/api/transaction").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH,"/api/delete/card").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH,"/api/delete/account").hasAuthority("CLIENT")
                .antMatchers("/web/index.html").permitAll();




        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout").deleteCookies("JSESSIONID");


        // turn off checking for CSRF tokens
        http.csrf().disable();

        //disabling frameOptions so h2-console can be accessed
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
}
