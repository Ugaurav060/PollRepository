package com.cg.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cg.model.User_table;


public class UserPrincipal implements UserDetails{

	private String userId;
	
	private String  password;
	
	private String name;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserPrincipal(String userId, String name, String password, List<GrantedAuthority> authorities) {
		    this.userId = userId;
	        this.name = name;
	        this.password = password;
	        this.authorities = authorities;
	    
	}
	
	 public static UserPrincipal create(User_table user) {
	        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	        authorities.add(new SimpleGrantedAuthority("USER"));

	        return new UserPrincipal(
	                user.getUserId(),
	                user.getName(),
	                user.getPassword(),
	                authorities
	        );
	    }
	 @Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
	public String getUsername() {
		
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(userId);
    }

	public UserPrincipal(String userId, String password, String name,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.authorities = authorities;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

public UserPrincipal() {
	// TODO Auto-generated constructor stub
}

    
    
}
