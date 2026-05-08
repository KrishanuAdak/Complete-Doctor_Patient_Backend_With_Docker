package com.example.jwt.jwt_author.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.jwt.jwt_author.model.User;
import com.example.jwt.jwt_author.repo.AuthorRepo;

@Component
public class UserDetailsImpl  implements  UserDetailsService{
    @Autowired
    private AuthorRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=repo.findByEmail(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        // List<SimpleGrantedAuthority> authority=List.of(new SimpleGrantedAuthority("ROLE_"+user.getRoles()));
        // new SimpleGrantedAuthority("ROLE_"+user.getRoles());
        // TODO Auto-generated method stub
        return org.springframework.security.core.userdetails.User.builder().username(user.getEmail()).password(user.getPassword()).roles(user.getRoles()).build();
    }
    
}
