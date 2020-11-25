package com.eriksdigital.exercise.service;

import com.eriksdigital.exercise.model.Member;
import com.eriksdigital.exercise.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final String USER_ROLE = "USER";

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = userRepository.findUserByName(username);

        if(member == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User authenticatedMember = (User) User.withDefaultPasswordEncoder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(USER_ROLE)
                .build();

        return authenticatedMember;
    }

    public Member save(Member member) {
        return userRepository.save(member);
    }

}
