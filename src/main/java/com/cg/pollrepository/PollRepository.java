package com.cg.pollrepository;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.model.User_table;



@Repository
public interface PollRepository extends JpaRepository<User_table, String> {
	@Query("select password from User_table  where userId=?1") 
	  String findByLoginId(String loginId);

	 @Query("from User_table where userId=?1") 
	  User_table findByLogin(String userId);

	  @Query("select name from User_table where userId=?1")
	String findByName(String userId);}
