package com.tweetApp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tweetApp.model.Tweets;
import com.tweetApp.model.Users;
import com.tweetApp.repository.UsersRepository;

@Service
public class TweetsService {
	
	@Autowired
	private UsersRepository usersRepository;
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Users registerNewUser(Users user) {
		Users newUser=usersRepository.save(user);
		log.debug(newUser.toString());
		return newUser;
	}
	
	public Users loginUser(String userId,String password) {
		Users user=usersRepository.findByUserIdAndPassword(userId,password);
		log.debug(user.toString());
		return user;
	}
	
	public Users resetPassword(String userId,String password) {
		Users user=usersRepository.findByUserId(userId);
		user.setPassword(password);
		usersRepository.save(user);
		return user;
	}
	
	public List<Tweets> retrunAllTweets(){
		List<Tweets> listOfTweets = new ArrayList<>();
        usersRepository.findAll().forEach(
        		user->
        			listOfTweets.addAll(user.getTweets())
        		);
        return listOfTweets;
	}
	
	public List<Users> retrunAllUsers(){
		List<Users> listOfUsers = new ArrayList<>();
        usersRepository.findUsersAndExcludePassword().forEach(listOfUsers::add);
        return listOfUsers;
	}
	
	public List<Tweets> returnTweetsOfUSer(String userId){
		Users user = usersRepository.findByUserId(userId);
		return user.getTweets();
		
	}
	
	public List<Users> returnUsersContainingName(String userId){
		List<Users> users=this.retrunAllUsers();
		return users.stream()
                .filter(x ->x.getUserId().indexOf(userId)>=0)
                .collect(Collectors.toList());
	    
	}
	
	public String postATweet(Tweets tweet,String userId) {
		Users user=usersRepository.findByUserId(userId);
    	List<Tweets> tweets=user.getTweets();
    	tweets.add(tweet);
    	usersRepository.save(user);
    	return "Posted the tweet successfully!";
	}
	
	public String deleteATweet(String userId,String tweetId) {
		Users user=usersRepository.findByUserId(userId);
		List<Tweets> tweets=user.getTweets();
		tweets.removeIf(t -> t.getTweetId() == tweetId);
		usersRepository.save(user);
		return "Tweet deleted succcessfully!";
	}
	
	public String replyATweet(String userId,String parentTweetId,Tweets reply) {
		log.debug("Inside tweets method!");
		reply.setParentTweetId(parentTweetId);
     	Users user=usersRepository.findByUserId(userId);
     	List<Tweets> tweets=user.getTweets();
     	tweets.add(reply);
     	Users user1=usersRepository.findByTweetsTweetId(parentTweetId);
     	log.debug(user1.toString());
     	List<Tweets> tweets1=user1.getTweets();
     	tweets1.forEach(t->{
     	if(t.getTweetId().contentEquals(parentTweetId)) {
     		t.getReplies().add(reply);
     	}
     	});
     	log.debug(tweets1.toString());
     	user1.setTweets(tweets1);
     	usersRepository.save(user1);
     	usersRepository.save(user);
     	return "Posted the reply!";
	}
	
	public String updateATweet(String userId,String tweetId,Tweets tweet) {
		Users user=usersRepository.findByUserId(userId);
		List<Tweets> tweets=user.getTweets();
		tweets.removeIf(t -> t.getTweetId() == tweetId);
		tweets.add(tweet);
		usersRepository.save(user);
		return "Updated tweet succcesfully";
	}
	
	public List<Tweets> likeATweet(String userId,String tweetId) {
		Users user=usersRepository.findByUserId(userId);
		List<Tweets> tweets=user.getTweets();
		tweets.forEach(t->{
			if(t.getTweetId().contentEquals(tweetId)) {
				t.setLikes(t.getLikes()+1);
				
			}
		});
		user.setTweets(tweets);
		usersRepository.save(user);
		return user.getTweets();
	}
}
