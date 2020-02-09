package com.cg.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;



@Entity
public class Questions {

	@SequenceGenerator(name="qid", initialValue=2500,sequenceName = "myQuestion", allocationSize=50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "qid")
	@Id
	private Integer questionId;
	private String question;
	@ManyToOne
	@JoinColumn(name="userId",referencedColumnName = "userId")
	private User_table userId;
	private Date expiryDate;
	
	

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public User_table getUserId() {
		return userId;
	}

	public void setUserId(User_table userId) {
		this.userId = userId;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Questions(Integer questionId, String question, User_table userId, Date expiryDate) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.userId = userId;
		this.expiryDate = expiryDate;
	}

	public Questions() {
		
	}

	@Override
	public String toString() {
		return "Questions [questionId=" + questionId + ", question=" + question + ", userId=" + userId + ", expiryDate="
				+ expiryDate + "]";
	}


	
	
	
		
	
}
