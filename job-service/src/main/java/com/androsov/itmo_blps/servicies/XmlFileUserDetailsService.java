package com.androsov.itmo_blps.servicies;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.users.AbstractUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.androsov.itmo_blps.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class XmlFileUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getByUsername(username);

            List<GrantedAuthority> authorities = new ArrayList<>();
            user.getRole().getPrivileges().forEach(privilege -> {
                authorities.add(new SimpleGrantedAuthority(privilege.getName()));
            });

            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);

        } catch (EntityNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
    }
}
