package com.prateek.reap.service;

import com.prateek.reap.entity.User;
import com.prateek.reap.entity.UserPrincipal;
import com.prateek.reap.entity.UserRole;
import com.prateek.reap.repository.LoginRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;


@Service("userDetail")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    LoginRepository loginRepository;

    Logger logger= LoggerFactory.getLogger(CustomUserDetailService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = loginRepository.findByEmailAndActive(email, true);

        if (isNull(user)) {
            logger.error("Bad credentials"+email);
            throw new UsernameNotFoundException("User Does Not Exists");

        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNotExpired = true;
        boolean accountNonLocked = true;

        return (UserDetails) new UserPrincipal(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNotExpired, accountNonLocked
                , getAuthorities(user), user);
    }

    private List<SimpleGrantedAuthority> getAuthorities(User roles) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (UserRole role : roles.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        return auths;

    }

    public Authentication getCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser;
    }
    }



