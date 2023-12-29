package com.example.delivery.authmodels;

import jakarta.persistence.*;

@Entity
@Table(name = "LOGIN_MODEL")
public class LogInModel {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id ;

	@Column(name = "User_Name")
	private String userName;

	@Column(name = "Password")
	private String password;





	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
