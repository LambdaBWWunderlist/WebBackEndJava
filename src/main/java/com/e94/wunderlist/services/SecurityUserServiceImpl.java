package com.e94.wunderlist.services;

import com.e94.wunderlist.exceptions.ResourceNotFoundException;
import com.e94.wunderlist.models.User;
import com.e94.wunderlist.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service(value = "securityUserService")
public class SecurityUserServiceImpl implements UserDetailsService
{

    @Autowired
    private UserRepository userrepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws ResourceNotFoundException {
        User user = userrepos.findByUsername(s.toLowerCase());
        if (user == null) {
            throw new ResourceNotFoundException("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }
}
