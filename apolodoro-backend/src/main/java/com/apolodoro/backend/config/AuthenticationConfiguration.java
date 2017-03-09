package com.apolodoro.backend.config;

import com.apolodoro.backend.model.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    private static final Logger LOG = Logger.getLogger(AuthenticationConfiguration.class.getName());

    @Autowired
    private Dao dao;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new MyAbstractUserDetailsAuthenticationProvider());
    }

    private class MyAbstractUserDetailsAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

        @Override
        protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

            String providedUsername = userDetails.getUsername();

            com.apolodoro.backend.model.User storedUser = dao.executeUserRequest(providedUsername);


            if (!providedUsername.equals(storedUser.getUsername())) {
                throw new UsernameNotFoundException(providedUsername);
            }


            if (!userDetails.getPassword().equals(storedUser.getPassword())){
                throw new UsernameNotFoundException(providedUsername);
            }

            LOG.info(providedUsername + " authenticated");

        }

        @Override
        protected UserDetails retrieveUser(String providedUsername, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

            com.apolodoro.backend.model.User user = dao.executeUserRequest(providedUsername);

            if (user.getRoles() == null) {
                throw new DisabledException("No role specified for user " + user.getUsername());
            }

            List<SimpleGrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            return new User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}

