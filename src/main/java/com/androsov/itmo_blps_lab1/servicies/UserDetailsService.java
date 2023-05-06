package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.entities.User;
import com.androsov.itmo_blps_lab1.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
@AllArgsConstructor
@Getter @Setter
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User" + username + " not found");

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                new ArrayList<AlwaysUser>()); // new ArrayList<AlwaysUser>() must be filled with roles
    }

    //TODO: This was made only for app work, need to be removed
    private static class AlwaysUser implements GrantedAuthority {
        @Override
        public String getAuthority() {
            return "ROLE_USER";
        }
    }
}
