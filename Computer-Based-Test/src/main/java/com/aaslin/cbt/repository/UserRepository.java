package com.aaslin.cbt.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aaslin.cbt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailAndCollegeUidAndCollegeRollNo(
        String email, String collegeUid, String collegeRollNo);

    // Fetch last USER id (UID...)
    @Query(value = "SELECT user_id FROM users WHERE user_id LIKE 'UID%' ORDER BY user_id DESC LIMIT 1", nativeQuery = true)
    String findLastUserIdForUsers();
}

