package com.aaslin.cbt.super_admin.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aaslin.cbt.common.model.User;
@Repository
public interface UsersRepository extends JpaRepository<User, String> {
	 Optional<User> findByUsername(String username);
	 
	 @Query(value = "SELECT user_id FROM users_cbt WHERE user_id LIKE 'SADM%' ORDER BY user_id DESC LIMIT 1", nativeQuery = true)
	    String findLastSuperAdminId();

	 @Query(value = "SELECT user_id FROM users_cbt WHERE user_id LIKE 'DEV%' ORDER BY user_id DESC LIMIT 1", nativeQuery = true)
	    String findLastDeveloperId();
	
    
}
