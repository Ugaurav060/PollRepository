package com.cg.pollrepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.model.Connections;

public interface ConnectionRepository extends JpaRepository<Connections, Integer> {

	@Query(value="select * from connections where following=?1", nativeQuery=true)
	List<Connections> findByFollower(String userId);

	@Query(value="select * from connections where user_id=?1", nativeQuery = true)
	List<Connections> findByFollowing(String userId);

}
