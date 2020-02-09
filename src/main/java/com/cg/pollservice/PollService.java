package com.cg.pollservice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.exception.PollManagementException;
import com.cg.model.Connections;
import com.cg.model.PollOptions;
import com.cg.model.PollQuestionsOptions;
import com.cg.model.Poll_answer;
import com.cg.model.Questions;
import com.cg.model.Request;
import com.cg.model.User_table;
import com.cg.pollrepository.ConnectionRepository;
import com.cg.pollrepository.CreatePollRepository;
import com.cg.pollrepository.PollAnswerRepository;
import com.cg.pollrepository.PollOptionRepository;
import com.cg.pollrepository.PollRepository;
import com.cg.pollrepository.RequestRepository;

@Service
public class PollService {

	@Autowired
	PollRepository repo;
	@Autowired
	CreatePollRepository createRepo;
	@Autowired
	RequestRepository requestRepo;
	@Autowired
	ConnectionRepository connectRepo;
	@Autowired
	CreatePollRepository questionsRepo;
	@Autowired
	PollAnswerRepository pollAnswerRepo;
	@Autowired
	PollOptionRepository pollOptionRepo;

	public String userId;
	public Questions ques = new Questions();

	public User_table getPassword(String userId, String password) throws PollManagementException {

		User_table user = new User_table();

		user = repo.findByLogin(userId);

		if (user == null) {
			throw new PollManagementException("No user exists with user id " + userId);
		}
		this.userId = userId;

		return user;

	}

	public User_table createAccount(User_table create) throws PollManagementException {
		String userIdPattern = "[A-Za-z0-9]{5,20}";
		if ((create.getUserId().matches(userIdPattern)) == false) {
			throw new PollManagementException("User Id doesn't match the appropriate pattern");
		}
		if (getLog(create.getUserId()) == 1) {
			throw new PollManagementException("This user Id already exists");
		}
		String namePattern = "[A-Z]{1}[A-Za-z ]{4,20}";
		if ((create.getName().matches(namePattern)) == false) {
			throw new PollManagementException("Name doesn't match the appropriate pattern");
		}

		return repo.save(create);
	}

	public Integer getLog(String userId) {
		User_table loginList = repo.findByLogin(userId);
		if (loginList == null) {
			return 0;
		} else {
			return 1;
		}
	}

	public Questions createPoll(Questions questions) throws PollManagementException {
		Questions question = new Questions();
		if (this.userId.equals(questions.getUserId().getUserId()) == false) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}
		try {
			question = createRepo.save(questions);
		} catch (Exception e) {
			throw new PollManagementException("Given user Id does not exist in the user table");
		}
		return question;

	}

	public PollOptions createOptions(PollOptions pollOptions) throws PollManagementException {

		/*
		 * Optional<Questions>
		 * list=questionsRepo.findById(pollOptions.getQuestionId().getQuestionId());
		 * System.out.println(list); List<Questions>
		 * l=list.stream().collect(Collectors.toList()); System.out.println(l);
		 * for(Questions q:l) { if(this.userId.equals(q.getUserId())==false) { throw new
		 * // * PollManagementException("This question Id "+pollOptions.getQuestionId().
		 * // *
		 * getQuestionId()+" does not belong to the logged in user Id "+this.userId); }
		 * }
		 */
		int flag = 0;
		List<Integer> qid = questionsRepo.findByUserId(this.userId);
		for (Integer i : qid) {
			if (pollOptions.getQuestionId().getQuestionId().equals(i)) {
				flag = 1;
			}
		}
		if (flag == 0) {
			throw new PollManagementException("This question Id " + pollOptions.getQuestionId().getQuestionId()
					+ " does not belong to the logged in user Id " + this.userId);
		}
		PollOptions options = new PollOptions();
		try {
			options = pollOptionRepo.save(pollOptions);
		} catch (Exception e) {
			throw new PollManagementException("No such question Id found in question table");
		}
		return options;
	}

	public String getName(String userId) throws PollManagementException {
		if (((this.userId).equals(userId) == false)) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}
		String name = repo.findByName(userId);
		try {
			if (name.equals(null)) {
				System.out.println("No name found");
			}
		} catch (Exception e) {
			throw new PollManagementException("No name found");
		}
		System.out.println(name);
		System.out.println(userId);
		return name;
	}

	public void sendRequest(Request requestInfo) throws PollManagementException {
		List<Connections> checkConnections = new ArrayList<Connections>();
		if (this.userId.equals(requestInfo.getRequestSentBy()) == false) {
			throw new PollManagementException(
					requestInfo.getRequestSentBy() + "cannot send a request as it is not a logged in id");
		}
		if (getLog(requestInfo.getRequestSentTo()) == 0) {
			throw new PollManagementException(
					"Invalid id " + requestInfo.getRequestSentTo() + "cannot accept a request");
		}
		
		else if(isRequestExist(requestInfo.getRequestSentTo(), requestInfo.getRequestSentBy())==1){
			throw new PollManagementException("You have already sent request to "+requestInfo.getRequestSentTo());
		}
		
		checkConnections=viewfollowing(requestInfo.getRequestSentBy());
		for(Connections c:checkConnections) {
			if(c.getFollowing().equals(requestInfo.getRequestSentTo())) {
				throw new PollManagementException("You are already following "+requestInfo.getRequestSentTo());
			}
		}
		
		requestRepo.save(requestInfo);
	}

	public Integer isRequestExist(String requestSentTo, String requestSentBy) {
		int flag = 0;
		List<String> requestList = requestRepo.findByRequestId(requestSentBy);
		System.out.println(requestSentTo);

		if (requestList.contains(requestSentTo)) {

			flag = 1;
		} else
			flag = 0;

		return flag;

	}

	public List<Request> viewRequest(String userId) throws PollManagementException {
		if (((this.userId).equals(userId) == false)) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}
		/*
		 * if (getLog(userId) == 0) { throw new
		 * PollManagementException("No user exists with user Id " + userId); }
		 */
		List<Request> requestList = requestRepo.findByRequestSentTo(userId);

		if (requestList.isEmpty()) {
			throw new PollManagementException("No request found for " + userId);
		}

		return requestList;
	}

	public void acceptRequest(Connections connect) throws PollManagementException {
		String userId = connect.getUserId().getUserId();
		System.out.println(userId);

		if (connect.getFollowing().equals(this.userId) == false) {
			throw new PollManagementException("You are not logged in with following id:" + connect.getFollowing());
		}
		Connections connects = new Connections();
		List<Request> requestList = viewRequest(connect.getFollowing());
		if (!requestList.isEmpty()) {

			for (Request request : requestList) {
				System.out.println(request.getRequestSentBy().equals(userId));
				System.out.println(request.getRequestSentBy());
				if (request.getRequestSentBy().equals(userId)) {
					requestRepo.deleteRequests(userId, connect.getFollowing());
					connectRepo.save(connect);
				} else {
					throw new PollManagementException("No requests to be accepted by:" + connect.getFollowing());
				}
			}
		}
		/*
		 * System.out.println(connects); return connects;
		 */
	}

	/*
	 * public void deleteRequest(String requestSentBy, String requestSentTo) {
	 * requestRepo.deleteRequests(requestSentBy,requestSentTo); }
	 */

	public List<Connections> viewfollower(String userId) throws PollManagementException {
		if (((this.userId).equals(userId) == false)) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}

		List<Connections> viewFollower = connectRepo.findByFollower(userId);
		if (viewFollower.isEmpty()) {
			throw new PollManagementException("No follower found for " + userId);
		}
		return viewFollower;
	}

	public List<Connections> viewfollowing(String userId) throws PollManagementException {
		if (((this.userId).equals(userId) == false)) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}

		List<Connections> viewFollowing = connectRepo.findByFollowing(userId);
		if (viewFollowing.isEmpty()) {
			throw new PollManagementException(userId + " is not following anyone");
		}
		return viewFollowing;
	}

	public List<PollQuestionsOptions> viewPoll(String userId) throws PollManagementException {
		if (((this.userId).equals(userId) == false)) {
			throw new PollManagementException("You are not logged in with user id " + this.userId);
		}
		Date today = null;
		int i = 0;
		if (getLog(userId) == 0) {
			throw new PollManagementException("No user exists with user Id " + userId);
		}
		List<Connections> list = viewfollowing(userId);
		List<PollQuestionsOptions> pollList = new LinkedList<PollQuestionsOptions>();
		Map<Integer, PollQuestionsOptions> pollMap = new HashMap<Integer, PollQuestionsOptions>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-DD");
		try {
			today = dateFormatter.parse(dateFormatter.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Questions> questionList = new ArrayList<Questions>();
		List<Questions> quesList=new ArrayList<Questions>();
		for (Connections c : list) {
			questionList = questionsRepo.findPoll(c.getFollowing());
			System.out.println(questionList);
		}
		if (questionList.isEmpty()) {
			throw new PollManagementException("No polls exist for" + userId);
		}
		/*
		 * List<Poll_answer> pollAnswerList = new ArrayList<Poll_answer>();
		 * 
		 * 
		 * 
		 * 
		 * pollAnswerList=pollAnswerRepo.findByUserId(userId);
		 * System.out.println(pollAnswerList);
		 * 
		 * 
		 * for(Poll_answer p:pollAnswerList) { for(Questions q:questionList) {
		 * if(p.getQuestionId().getQuestionId().equals(q.getQuestionId())){
		 * questionList.remove(q); } } }
		 * 
		 * 
		 * if(questionList.isEmpty()) { throw new
		 * PollManagementException("No polls exist for"+userId); }
		 */
		 
		for (Questions q : questionList) {

			if (today.compareTo(q.getExpiryDate()) < 0) {
				System.out.println("Not Expired");
				PollQuestionsOptions pollQuestionsOptions = new PollQuestionsOptions();
				System.out.println(q.getQuestionId());
				List<String> optionList = pollOptionRepo.findByQuestionIdOptions(q.getQuestionId());
				if (optionList.isEmpty()) {
					throw new PollManagementException("No options found");
				}
				pollQuestionsOptions.setQuestionId(q.getQuestionId());
				pollQuestionsOptions.setQuestion(q.getQuestion());
				pollQuestionsOptions.setOptionList(optionList);

				// pollSet.add(pollQuestionsOptions);
				pollList.add(pollQuestionsOptions);

			}
		}

		return pollList;
	}

	public Poll_answer createPollAnswer(Poll_answer answer) throws PollManagementException {

		if (answer.getUserId().getUserId().equals(this.userId) == false) {
			throw new PollManagementException(
					"You cannot answer the poll as it is not a logged in ID " + answer.getUserId());
		}

		List<Poll_answer> list = pollAnswerRepo.findByUserIdAndQuestionId(answer.getUserId().getUserId(),
				answer.getQuestionId().getQuestionId());
		if ((list.isEmpty()) == false) {
			throw new PollManagementException(answer.getUserId().getUserId()
					+ " have already answered the question with question Id " + answer.getUserId().getUserId());
		}
		Poll_answer poll = new Poll_answer();
		try {
			poll = pollAnswerRepo.save(answer);
		} catch (Exception e) {
			throw new PollManagementException("No such question id,user id or option id found");
		}
		return poll;
	}

	public Map<Integer, Double> viewStatus(Integer questionId) throws PollManagementException {

		List<Poll_answer> l = pollAnswerRepo.findByQuestion(questionId);

		for (Poll_answer q : l) {
			if (this.userId.equals(q.getQuestionId().getUserId().getUserId()) == false) {
				throw new PollManagementException(
						"This question Id " + questionId + " does not belong to the logged in user Id " + this.userId);
			}
		}

		Integer questionCount = pollAnswerRepo.findByQuestionId(questionId);
		if (questionCount == 0) {
			throw new PollManagementException("No one has answered the poll with id " + questionId);
		}

		int optionCount = 0;
		Map<Integer, Double> optionStatus = new HashMap<Integer, Double>();
		List<Integer> optionList = pollOptionRepo.findByQuestionId(questionId);
		if (optionList.isEmpty()) {
			throw new PollManagementException("No options for poll with id " + questionId);
		}
		for (Integer option : optionList) {
			Double percent = 0.0;
			optionCount = pollAnswerRepo.calculateCount(questionId, option);

			percent = (double) ((optionCount / questionCount) * 100);
			optionStatus.put(option, percent);
			optionCount = 0;
		}
		return optionStatus;
	}

}
