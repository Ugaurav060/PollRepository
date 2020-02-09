package com.cg.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Request {
	@SequenceGenerator(name="reqid", initialValue=2500,sequenceName = "requestSeq", allocationSize=50)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "reqid")
	@Id
	private Integer requestId;
	
	private String requestSentTo;
	private String requestSentBy;
	public Integer getRequestId() {
		return requestId;
	}

	/*
	 * public void setRequestId(Integer requestId) { this.requestId = requestId; }
	 */
	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", requestSentBy=" + requestSentBy + ", requestSentTo="
				+ requestSentTo + "]";
	}
	public Request() {
		super();
	}
	
	

	/*
	 * public Request(Integer requestId, String requestSentBy, String requestSentTo)
	 * { super(); this.requestId = requestId;
	 * 
	 * this.requestSentTo = requestSentTo; this.requestSentBy = requestSentBy; }
	 */
	public String getRequestSentBy() {
		return requestSentBy;
	}
	public void setRequestSentBy(String requestSentBy) {
		this.requestSentBy = requestSentBy;
	}
	public String getRequestSentTo() {
		return requestSentTo;
	}
	public void setRequestSentTo(String requestSentTo) {
		this.requestSentTo = requestSentTo;
	}

	public Request(Integer requestId, String requestSentTo, String requestSentBy) {
		super();
		this.requestId = requestId;
		this.requestSentTo = requestSentTo;
		this.requestSentBy = requestSentBy;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

 

	
	
}
