package com.aaslin.cbt.serviceImplementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aaslin.cbt.entity.User;
import com.aaslin.cbt.repository.UserRepository;
import com.aaslin.cbt.service.UserService;
import com.aaslin.cbt.utils.CustomUserIdGenerator;

@Service
public class UserServiceImple implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User login(User user) {
        Optional<User> userOpt = userRepository.findByEmailAndCollegeUidAndCollegeRollNo(
                user.getEmail(), user.getCollegeUid(), user.getCollegeRollNo()
        );

        if (userOpt.isPresent()) {
            return userOpt.get(); // Could be ADMIN or USER
        } else {
            // Generate new USER ID (ignore ADMINxxx)
            String lastId = userRepository.findLastUserIdForUsers();
            String newId = CustomUserIdGenerator.generateNextUserId(lastId);

            user.setUserId(newId);

            // Default role = USER
            user.setRole(User.Role.USER);

            return userRepository.save(user);
        }
    }
}