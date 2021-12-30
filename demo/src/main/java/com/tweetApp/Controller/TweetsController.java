package com.tweetApp.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.tweetApp.model.Tweets;
import com.tweetApp.model.Users;
import com.tweetApp.services.TweetsService;
import java.util.List;




@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetsController
{
    @Autowired
    TweetsService tweetsService;
    Logger log = LoggerFactory.getLogger(this.getClass());

    
    //Register a user
    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody Users user){
    	log.debug("Inside of registerUser() method ");
    	try {
    		
		Users newUser=tweetsService.registerNewUser(user);
		
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    	}
    	catch(Exception e) {
    		
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
 
    	
    }
  //login a registered user  
    @GetMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestParam String userId,@RequestParam String password){
    	log.debug("Inside of loginUser() method ");
    	try {
    		
    		Users newUser=tweetsService.loginUser(userId,password);
    		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        	}
        	catch(Exception e) {
        		
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        	}
    }
    
    //forgot password
    @GetMapping("/{userId}/forgot")
    public ResponseEntity<Object> resetPassword(@PathVariable("userId") String userId,@RequestBody String password){
    	log.debug("Inside of resetPassword() method ");
try {
    		
    		Users newUser=tweetsService.resetPassword(userId,password);
    		log.debug(String.format("%s", newUser));
    		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        	}
        	catch(Exception e) {
        		
                return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        	}
    }
    
    

    //Gets all the tweets of all the users
    @GetMapping("/all")
    public ResponseEntity<List<Tweets>> getAllTweeetsOfUser()
    {
    	log.debug("Inside of getAllTweetsOfUser() method ");
        try
        {
            List<Tweets> listOfTweets=tweetsService.retrunAllTweets();
            if(listOfTweets.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfTweets, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //Returns all the users 
    @GetMapping("/users/all")
    public ResponseEntity<List<Users>> getAllUsers()
    {
    	log.debug("Inside of getAllUsers() method ");
        try
        {
        	List<Users> listOfUsers=tweetsService.retrunAllUsers();
            if(listOfUsers.isEmpty())
            {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //returns tweets of particular user
    @GetMapping("/{userId}")
    public ResponseEntity<List<Tweets>> getTweetByuserId(@PathVariable("userId") String userId)
    {
    	log.debug("Inside of getTweetsByUserId() method ");
        try
        {
            
        	List<Tweets> tweets=tweetsService.returnTweetsOfUSer(userId);
            return new ResponseEntity<>(tweets, HttpStatus.OK);
        }
        catch (Exception e)
        {
        	
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //searchByUserName usrname canbe complete or partial 
    @GetMapping("/user/search/{userId}")
    public ResponseEntity<List<Users>> getUserByName(@PathVariable("userId")String userId) {
    	log.debug("Inside of getUserByName() method ");
    	 try
         {
             
         	List<Users> users=tweetsService.returnUsersContainingName(userId);
             return new ResponseEntity<>(users, HttpStatus.OK);
         }
         catch (Exception e)
         {
        	log.error(e.getMessage());
             return new ResponseEntity<>( null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
//post a tweet
    @PostMapping("/{userId}/add")
    public ResponseEntity<String> postTweet(@RequestBody Tweets tweet,@PathVariable("userId") String userId)
    {
    	log.debug("Inside of postTweet() method ");
        try
        {
        	String msg=tweetsService.postATweet(tweet, userId);
            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
        	log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //delete a tweet
    
    @DeleteMapping("/{userId}/delete/{tweetId}")
    public ResponseEntity<String> deleteTweet(@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
	log.debug("Inside deleetTweet() method");
    	try
	{
		String msg=tweetsService.deleteATweet(userId, tweetId);
	    return new ResponseEntity<>(msg, HttpStatus.CREATED);
	}
	catch (Exception e)
	{
		log.error(e.getMessage());
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }
    
    //reply to a tweet
    @PostMapping("/{userId}/reply/{parentTweetId}")
    public ResponseEntity<String> replyATweet(@RequestBody Tweets reply,@PathVariable("userId") String userId,@PathVariable("parentTweetId") String parentTweetId) {
    	log.debug("Inside of replyATweet() method ");
    	try
         {
    		String msg=tweetsService.replyATweet(userId, parentTweetId, reply);
             return new ResponseEntity<>(msg, HttpStatus.CREATED);
         }
         catch (Exception e)
         {
         	log.error(e.getMessage());
             return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }
    
    //update a tweet
    @PutMapping("/{userId}/update/{tweetId}")
    public ResponseEntity<String> updateTweet(@RequestBody Tweets tweet,@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
    	log.debug("Inside of updateTweet() method ");
    	try
    	{
    		String msg=tweetsService.updateATweet(userId, tweetId,tweet);
    	    return new ResponseEntity<>(msg, HttpStatus.CREATED);
    	}
    	catch (Exception e)
    	{
    		log.error(e.getMessage());
    	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
    
    //like a tweet
    @PutMapping("/{userId}/like/{tweetId}")
    public ResponseEntity<List<Tweets>> likeATweet(@PathVariable("userId") String userId,@PathVariable("tweetId") String tweetId) {
    	log.debug("Inside of likeATweet() method ");
    	try
    	{
    		List<Tweets> tweets=tweetsService.likeATweet(userId,tweetId);
    	    return new ResponseEntity<>(tweets, HttpStatus.CREATED);
    	}
    	catch (Exception e)
    	{
    		log.error(e.getMessage());
    	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}
