package com.cg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Poll_answer {

	
	@SequenceGenerator(name="tid", initialValue=3000,sequenceName = "pollAnswerSeq", allocationSize=100)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "tid")
	@Id
	private Integer pollAnswerId;
	
	@ManyToOne
	@JoinColumn(name="questionId",referencedColumnName = "questionId")
	private Questions questionId;
	
	@ManyToOne
	@JoinColumn(name="optionId",referencedColumnName = "optionId")
	private PollOptions optionId;
	
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private User_table userId;
	
	
	

	public Integer getPollAnswerId() {
		return pollAnswerId;
	}

	public void setPollAnswerId(Integer pollAnswerId) {
		this.pollAnswerId = pollAnswerId;
	}

	public Questions getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Questions questionId) {
		this.questionId = questionId;
	}

	
	public PollOptions getOptionId() {
		return optionId;
	}

	public void setOptionId(PollOptions optionId) {
		this.optionId = optionId;
	}

	public User_table getUserId() {
		return userId;
	}

	public void setUserId(User_table userId) {
		this.userId = userId;
	}
	

	public Poll_answer(Integer pollAnswerId, Questions questionId, PollOptions optionId, User_table userId) {
		super();
		this.pollAnswerId = pollAnswerId;
		this.questionId = questionId;
		this.optionId = optionId;
		this.userId = userId;
		
	}

	public Poll_answer() {
		
	}
	
	

	
	
	
	
}	