package com.coworking.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.coworking.model.User;
import com.coworking.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService  {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
	        throws UsernameNotFoundException {

	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() ->
	                    new UsernameNotFoundException(
	                            "Invalid email or password"));

	    return new CoworkingUserDetails(user);

	}

}
