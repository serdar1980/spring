package ru.serdar1980.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static List<User> users = new ArrayList();

    public UserDetailsServiceImpl() {
        users.add(new User("erin", "123", "ADMIN"));
        users.add(new User("mike", "234", "ADMIN"));
        users.add(new User("admin", "admin1234", "ADMIN"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserbyUername(username);

        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRoles());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private User findUserbyUername(String username) {
        Optional<User> user = users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny();
        if (!user.isPresent()) {
            throw null;
        }
        return user.get();
    }
}
