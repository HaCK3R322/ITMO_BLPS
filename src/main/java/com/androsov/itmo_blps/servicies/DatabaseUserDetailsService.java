package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.entities.User;
import com.androsov.itmo_blps.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
@Getter @Setter
public class DatabaseUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User" + username + " not found");

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                getAuthorities(user));
    }

    private List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        user.getRole().getPrivileges().forEach(privilege -> grantedAuthorities.add((GrantedAuthority) privilege::getName));

        return grantedAuthorities;
    }
}
