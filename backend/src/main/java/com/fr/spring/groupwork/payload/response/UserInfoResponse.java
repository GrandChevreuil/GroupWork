package com.fr.spring.groupwork.payload.response;

import java.util.List;

import com.fr.spring.groupwork.models.enums.ETypeUser;

/**
 * File UserInfoResponse.java
 * This class represents the response payload for user information.
 * It includes user ID, username, email, roles, type of user, and active status.
 * @author Mathis Mauprivez
 * @date 18/06/2025
 */

public class UserInfoResponse {
	private Long id;
	private String username;
	private String email;
	private List<String> roles;
	private ETypeUser typeUser;
	private boolean isActive;
	private Long classeId;
	private String classeName;

	public UserInfoResponse(Long id, String username, String email, List<String> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public UserInfoResponse(Long id, String username, String email, List<String> roles, ETypeUser typeUser, boolean isActive, Long classeId, String classeName) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.typeUser = typeUser;
		this.isActive = isActive;
		this.classeId = classeId;
		this.classeName = classeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}
	
	public ETypeUser getTypeUser() {
		return typeUser;
	}
	
	public void setTypeUser(ETypeUser typeUser) {
		this.typeUser = typeUser;
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getClasseId() {
		return classeId;
	}

	public String getClasseName() {
		return classeName;
	}
}
