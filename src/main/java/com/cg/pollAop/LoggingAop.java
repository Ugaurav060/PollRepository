package com.cg.pollAop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.cg.exception.PollManagementException;

@Configuration
@Aspect

public class LoggingAop {
	Logger logger = LoggerFactory.getLogger(LoggingAop.class);

	@AfterThrowing (pointcut = "execution(* com.cg.pollservice.PollService.*(..))", throwing = "ex")
    public void logAfterThrowingAllMethods(PollManagementException ex)  { 
		logger.error(ex.getMessage());
	}
	@AfterThrowing (pointcut = "execution(* com.cg.controller.UserController.createAccount(..))", throwing = "ex")
    public void logAfterThrowingAllControllerMethods(PollManagementException ex)  { 
		logger.error(ex.getMessage());
	}
	
}
