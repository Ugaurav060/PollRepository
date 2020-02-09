package com.cg.pollrepository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cg.model.Request;
import com.cg.model.User_table;

public interface RequestRepository extends JpaRepository<Request, Integer>{

	@Query("select requestSentTo from Request where requestSentBy=?1")
	List<String> findByRequestId(String requestSentBy);

	@Query("from Request where requestSentTo=?1")
	List<Request> findByRequestSentTo(String userId);

	
	@Modifying
	@Query("delete from Request r where r.requestSentBy=:requestSentBy AND r.requestSentTo=:requestSentTo")
	@Transactional
	void deleteRequests(@Param("requestSentBy")String requestSentBy,@Param("requestSentTo")String requestSentTo);
	 

}
