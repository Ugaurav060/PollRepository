package com.cg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity

public class Connections {

	

	
	
	@GeneratedValue
	@Id
	
	private Integer connectionId;
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private User_table userId;
	private String following;

	public Connections() {
		// TODO Auto-generated constructor stub
	}

	public Connections(Integer connectionId, User_table userId, String following) {
		super();
		this.connectionId = connectionId;
		this.userId = userId;
		this.following = following;
	}

	public Integer getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Integer connectionId) {
		this.connectionId = connectionId;
	}

	public User_table getUserId() {
		return userId;
	}

	public void setUserId(User_table userId) {
		this.userId = userId;
	}

	public String getFollowing() {
		return following;
	}

	public void setFollowing(String following) {
		this.following = following;
	}

	@Override
	public String toString() {
		return "Connections [connectionId=" + connectionId + ", userId=" + userId + ", following=" + following + "]";
	}
	
	 
	
	
	
	
	
	

}
