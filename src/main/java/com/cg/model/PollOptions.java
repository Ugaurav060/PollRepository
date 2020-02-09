package com.cg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class PollOptions {

	@SequenceGenerator(name="oid", initialValue=200,sequenceName = "optionSeq", allocationSize=50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "oid")
	@Id
	private Integer optionId;
	@ManyToOne
	@JoinColumn(name="questionId",referencedColumnName = "questionId")
	private Questions questionId;
	private String pollOption;
	public Integer getOptionId() {
		return optionId;
	}
	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}
	public Questions getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Questions questionId) {
		this.questionId = questionId;
	}
	public String getPollOption() {
		return pollOption;
	}
	public void setPollOption(String pollOption) {
		this.pollOption = pollOption;
	}
	public PollOptions(Integer optionId, Questions questionId, String pollOption) {
		super();
		this.optionId = optionId;
		this.questionId = questionId;
		this.pollOption = pollOption;
	}
	public PollOptions() {
		
	}
	@Override
	public String toString() {
		return "PollOptions [optionId=" + optionId + ", questionId=" + questionId + ", pollOption=" + pollOption + "]";
	}
	
	
	
	
}
