package com.clouding2u.service;

public enum UserQueryBy {
 ById("id"),	
 ByUsername("username"),
 ByFirstname("firstName"),
 byLastname("lastName");
 private UserQueryBy(String value) {
	// TODO Auto-generated constructor stub
	queryBy=value;
}
 private String queryBy;
}
