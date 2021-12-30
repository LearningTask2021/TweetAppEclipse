package com.tweetApp.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import com.tweetApp.model.Users;

public interface UsersRepository extends MongoRepository<Users,String>{
	
	@Query(value="{}", fields="{ password : 0,tweets:0}")
	List<Users> findUsersAndExcludePassword();
	Users findByUserId(String userId);
	Users findByTweetsParentTweetId(String parentTweetId);
	Users findByTweetsTweetId(String tweetId);
	Users findByUserIdAndPassword(String userId,String password);
	
	
}
