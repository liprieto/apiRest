package com.apirest.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.app.entity.User;

@Repository
public interface userRepository extends JpaRepository<User, Long>{
	

}
