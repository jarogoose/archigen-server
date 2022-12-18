package org.jarogoose.archigen.web.auth;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final UserStorage userStorage;
    private final PasswordEncoder encoder;

    public AuthService(UserStorage userStorage, PasswordEncoder encoder) {
        this.userStorage = userStorage;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userStorage.findByUsername(username)
                .map(result -> result)
                .orElseThrow(() -> new UsernameNotFoundException("[LOGIN] user does not exist"));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(entity.getRole()));

        return User
                .withUsername(entity.getUsername())
                .password(entity.getPassword())
                .authorities(authorities)
                .build();
    }

    public void registerUSer(RegisterUserRequest request) {
        UserEntity entity = new UserEntity();
        entity.setUsername(request.username());
        entity.setPassword(encoder.encode(request.password()));
        entity.setRole(request.role());
        entity.setStatus("Active");

        userStorage.save(entity);
    }

}
