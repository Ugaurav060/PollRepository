package com.cg.pollrepository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cg.model.Poll_answer;

public interface PollAnswerRepository extends JpaRepository<Poll_answer, Integer> {

	
	@Query(value="select count(*) from poll_answer where question_Id=?1",nativeQuery = true)
	
	Integer findByQuestionId(Integer questionId);

	
	@Query(value="select count(*) from poll_answer where question_id=?1 AND option_id=?2",nativeQuery = true)

	Integer calculateCount(Integer questionId, Integer option);
	

	@Query(value="select * from poll_answer where question_id=?1",nativeQuery=true)
	List<Poll_answer> findByQuestion(Integer questionId);


	@Query(value="select * from poll_answer where user_id=?1",nativeQuery=true)
	List<Poll_answer> findByUserId(String userId);
	
	@Query(value="select * from poll_answer where user_id=?1",nativeQuery=true)
	List<Poll_answer> findByUserIdAndQuestionId(String userId,Integer questionId);
}
