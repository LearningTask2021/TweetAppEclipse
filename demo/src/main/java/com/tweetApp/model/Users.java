package com.tweetApp.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "Users")
	public class Users
	{
	
	private String firstName;
	private String lastName;
	@Id
	private String userId;
	private String password;
	private String email;
	private String contactNumber;
	private List<Tweets> tweets=new ArrayList<>();
	
	

}
