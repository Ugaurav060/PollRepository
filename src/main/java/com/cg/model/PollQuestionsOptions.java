package com.cg.model;

import java.util.List;

public class PollQuestionsOptions {
	private Integer questionId;
	private String question;
	private List<String> optionList;
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
	public List<String> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}
	public PollQuestionsOptions(Integer questionId, String question, List<String> optionList) {
		super();
		this.questionId = questionId;
		this.question = question;
		this.optionList = optionList;
	}
	@Override
	public String toString() {
		return "PollQuestionsOptions [questionId=" + questionId + ", question=" + question + ", optionList="
				+ optionList + "]";
	}
	public PollQuestionsOptions() {
		
	}
	
	

}
