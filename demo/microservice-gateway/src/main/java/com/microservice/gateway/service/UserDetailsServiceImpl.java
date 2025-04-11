package com.microservice.gateway.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.gateway.persistence.entity.UserEntity;
import com.microservice.gateway.persistence.repository.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return Mono.just(userEntity)
            .map(user -> {
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                user.getRoles().forEach(role ->
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
                );
                user.getRoles().stream()
                    .flatMap(role -> role.getPermissionList().stream())
                    .forEach(permission ->
                        authorities.add(new SimpleGrantedAuthority(permission.getName()))
                );

                return new User(
                    user.getUsername(),
                    user.getPassword(),
                    user.isEnabled(),
                    user.isAccountNoExpired(),
                    user.isCredentialNoExpired(),
                    user.isAccountNoLocked(),
                    authorities
                );
            });
    }
    
}
