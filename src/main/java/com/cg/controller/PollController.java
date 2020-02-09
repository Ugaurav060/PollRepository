package com.cg.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.exception.PollManagementException;
import com.cg.model.Connections;
import com.cg.model.PollOptions;
import com.cg.model.PollQuestionsOptions;
import com.cg.model.Poll_answer;
import com.cg.model.Questions;
import com.cg.model.Request;
import com.cg.pollservice.PollService;

@RestController
@RequestMapping("/api/polls")
public class PollController {
	
	@Autowired
	PollService pollService;
	
	
	@PostMapping(path="/createpoll")
	public Questions createPoll(@RequestBody Questions questions) {
		Questions question=new Questions();
		try {
	    question= pollService.createPoll(questions);
		}
		catch (PollManagementException e) {
			System.err.println(e.getMessage());
		}
		return question;
		
	}
	@PostMapping(path="/addoptions")
	public PollOptions addOptions(@RequestBody PollOptions pollOptions ) {
		
	    PollOptions options=new PollOptions();
	    try{
	    	options=pollService.createOptions(pollOptions);
	    }catch (PollManagementException e) {
			System.err.println(e.getMessage());
		}
		return options;
	}
	
	@GetMapping(path="/intermediate/{userId}", produces = "application/json")
	
	public String getName(@PathVariable String userId) {
		String name="";
		try {
		name= pollService.getName(userId);
		}
		catch (PollManagementException e) {
			return e.getMessage();
		}
		return name;
	}
	
	@PostMapping(path="/createrequest")
	public String sendRequest(@RequestBody Request requestInfo) {
		
		
		try{
			pollService.sendRequest(requestInfo);
			return "Request sent successfully";
		}
		catch (PollManagementException e) {
			System.err.println(e.getMessage());
			return "Invalid Id";
		}
		
	}
	
	@GetMapping(path="/createrequest/{requestSentTo}/{requestSentBy}")
	public Integer isRequestExist(@PathVariable String requestSentTo,@PathVariable String requestSentBy) {
		

		return pollService.isRequestExist(requestSentTo,requestSentBy);
	}
	@GetMapping(path="/viewrequest/{userId}")
	public List<Request> viewRequest(@PathVariable String userId) {
		
		List<Request> requestSentTo=new ArrayList<Request>();
		try {
		 requestSentTo= pollService.viewRequest(userId);
		}
		catch (PollManagementException e) {
			System.err.println(e.getMessage());
		}
		return requestSentTo;
	}
	
	/*
	 * @PostMapping(path="/createConnection") public Connections
	 * createConnection(@RequestBody Connections connect) {
	 * 
	 *
	 * 
	 * }
	 */
	@PostMapping(path="/acceptRequest") 
	public String acceptRequest(@RequestBody Connections connect) {
	  Connections connects=new Connections();
	  try {
		 pollService.acceptRequest(connect);
		 return "Request Accepted";
	} catch (PollManagementException e) {
		System.out.println(e.getMessage());
		return e.getMessage();
	}
		/* return connects; */
			  
	  }
	
	
	
	/*
	 * @DeleteMapping(path="/deleteRequest/{requestSentBy}/{requestSentTo}") public
	 * void deleteRequest(@PathVariable String requestSentBy,@PathVariable String
	 * requestSentTo) {
	 * 
	 * pollService.deleteRequest(requestSentBy,requestSentTo); }
	 */
	  
	  @GetMapping(path="/viewfollower/{userId}")
		public List<Connections> viewfollower(@PathVariable String userId) {
			
			List<Connections> viewFollower=new ArrayList<Connections>();
			try {
			      viewFollower=pollService.viewfollower(userId);
			}
			catch (PollManagementException e) {
				System.err.println(e.getMessage());
			}
			return viewFollower;
		}
	  
	  @GetMapping(path="/viewfollowing/{userId}")
		public List<Connections> viewfollowing(@PathVariable String userId) {
			

			
			List<Connections> viewFollowing=new ArrayList<Connections>(); 
			try{
				viewFollowing=pollService.viewfollowing(userId);
			}catch (PollManagementException e) {
				System.err.println(e.getMessage());
			}
			return viewFollowing;
		}
	  
	  @GetMapping(path="/viewPoll/{userId}")
		public List<PollQuestionsOptions> viewPoll(@PathVariable String userId) {
			
			List<PollQuestionsOptions>  viewPoll=new ArrayList<PollQuestionsOptions>();
			try{
				viewPoll=pollService.viewPoll(userId);
			}
			catch (PollManagementException e) {
				System.err.println(e.getMessage());
			}
			return viewPoll;
		}
	  
		@PostMapping(path="/sendPollAnswer")
		public Poll_answer createPollAnswer(@RequestBody Poll_answer answer) {
			
			Poll_answer poll=new Poll_answer();
			try{
				poll=pollService.createPollAnswer(answer);
			}catch (PollManagementException e) {
				System.err.println(e.getMessage());
			}
			return poll;
			
		}
		
		@GetMapping(path="/viewStatus/{questionId}")
		public java.util.Map<Integer, Double> viewStatus(@PathVariable Integer questionId) {
			

			java.util.Map<Integer,Double> map=new HashMap<Integer,Double>();
			try {
			map=pollService.viewStatus(questionId);
			}
			catch (PollManagementException e) {
				System.err.println(e.getMessage());
			}
			return map;
		}
		
}
