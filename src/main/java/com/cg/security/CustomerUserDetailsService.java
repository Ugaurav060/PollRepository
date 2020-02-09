package com.cg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.model.User_table;
import com.cg.pollrepository.PollRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService{

	@Autowired
	PollRepository pollRepository;
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		 User_table user = pollRepository.findById(userId).orElseThrow(
		            () -> new UsernameNotFoundException("User id doesn't exist "+userId)
		        );

		        return UserPrincipal.create(user);
		    }
	
	@Transactional
    public UserDetails loadUserById(String userId) {
        User_table user = pollRepository.findById(userId).orElseThrow(
            () -> new UsernameNotFoundException("User id doesn't exist "+userId)
        );

        return UserPrincipal.create(user);
    }

}
