package com.cg.pollrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.model.PollOptions;

public interface PollOptionRepository extends JpaRepository<PollOptions, Integer> {

	@Query(value="select poll_option from Poll_Options where question_Id=?1",nativeQuery=true)
	List<String> findByQuestionIdOptions(Integer questionId);

	@Query(value="select option_Id from Poll_Options where question_Id=?1",nativeQuery = true)
	List<Integer> findByQuestionId(Integer questionId);

}
