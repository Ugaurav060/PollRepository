package com.cg.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.PollManagementException;
import com.cg.model.User_table;
import com.cg.payload.ApiResponse;
import com.cg.payload.JwtAuthenticationResponse;
import com.cg.pollservice.PollService;
import com.cg.security.JwtTokenProvider;




@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	PollService pollService;
	
	    @Autowired
	    AuthenticationManager authenticationManager;

	    
	    @Autowired
	    PasswordEncoder passwordEncoder;

	    @Autowired
	    JwtTokenProvider tokenProvider;
	    
	   
	
	@PostMapping(path="/CreateAccount")
	
	public ResponseEntity<?> createAccount(@RequestBody User_table create) throws PollManagementException {
		
		 
		String passwordPattern="[A-Za-z0-9_]{5,20}";
		ResponseEntity<?> response = null;
		  if((create.getPassword().matches(passwordPattern))==false) {
			  try{ throw new PollManagementException("Password doesn't match the appropriate pattern"); 
			  }catch (PollManagementException e) {
				System.err.println(e.getMessage());
				response= new ResponseEntity(new ApiResponse(false, "User not successfully created"),HttpStatus.BAD_REQUEST);

			}
			  }
		
		
		  // Creating user's account
		  if((create.getPassword().matches(passwordPattern))==true) {
        User_table user = new User_table(create.getUserId(), create.getPassword(),create.getName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        try {
        User_table result = pollService.createAccount(user);
        response=new ResponseEntity(new ApiResponse(true, "User successfully created"),HttpStatus.ACCEPTED);
        }
        catch (PollManagementException e) {
			System.err.println(e.getMessage());
			response= new ResponseEntity(new ApiResponse(false, "User not successfully created"),HttpStatus.BAD_REQUEST);

		}
		}
		return response;
       

       	}

	
	@PostMapping(path="/login")
	public ResponseEntity<?> validateLogin(@RequestParam String userId,@RequestParam String password) throws PollManagementException  {
		User_table user=new User_table();
	try {
		 user=pollService.getPassword(userId,password);
	    }catch (PollManagementException e) {
			System.err.println(e.getMessage());
		}
		 Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                       user.getUserId(),
	                       password
	                )
	        );
	

	        SecurityContextHolder.getContext().setAuthentication(authentication);

	        String jwt = tokenProvider.generateToken(authentication);
	        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
		/*
		 * String pass="";
		 * 
		 * try { pass=pollService.getPassword(loginId); if(pass.equals(password)) {
		 * return "Login Successful"; } } catch (PollManagementException e) {
		 * System.out.println(e.getMessage()); }
		 * 
		 * return "Invalid Login";
		 */
	}
	
	
	/*
	 * public Integer getLog(String loginId) { System.out.println(loginId);
	 * 
	 * return pollService.getLog(loginId); }
	 */
	
	

}


