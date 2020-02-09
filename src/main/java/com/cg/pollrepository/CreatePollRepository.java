package com.cg.pollrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.model.Questions;

@Repository
public interface CreatePollRepository extends JpaRepository<Questions, Integer> {

	@Modifying
	@Query(value="select * from questions where user_id=?1", nativeQuery=true)
	List<Questions> findPoll(String userId);

	@Query(value="select question_id from questions where user_id=?1",nativeQuery = true)
	List<Integer> findByUserId(String userId);
	
	

}
