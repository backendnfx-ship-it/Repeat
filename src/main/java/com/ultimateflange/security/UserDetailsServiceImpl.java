package com.ultimateflange.security;

import com.ultimateflange.model.User;
import com.ultimateflange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // ✅ Database se user fetch karo
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // ✅ Spring Security User build karo - String.name() nahi, user.getEmail() use karo
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())      // ✅ user.getEmail() use karo
                .password(user.getPassword())   // ✅ Encoded password
                .authorities(new ArrayList<>()) // ✅ Empty authorities list
                .build();
    }
}