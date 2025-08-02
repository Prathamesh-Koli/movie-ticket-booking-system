package com.bookar.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bookar.entities.*;
import java.util.List;
import java.util.Optional;


public interface CustomerDao extends JpaRepository<User, Long> {
	
	User findByEmailAndPassword(String email, String password);
	User findByEmail(String email);
	boolean existsByEmail(String email);
	
}
