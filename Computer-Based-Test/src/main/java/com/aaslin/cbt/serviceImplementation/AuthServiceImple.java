package com.aaslin.cbt.serviceImplementation;

import com.aaslin.cbt.entity.Contest;
import com.aaslin.cbt.entity.User;
import com.aaslin.cbt.repository.ContestRepository;
import com.aaslin.cbt.repository.UserRepository;
import com.aaslin.cbt.service.AuthService;
import com.aaslin.cbt.utils.CustomUserIdGenerator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthServiceImple implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private HttpSession session;

    @Override
    public User authenticateUserAndJoinContest(User userInput) {

        // Check if user exists in DB
        Optional<User> existingUserOpt = userRepository.findByEmailAndCollegeUidAndCollegeRollNo(
                userInput.getEmail(),
                userInput.getCollegeUid(),
                userInput.getCollegeRollNo()
        );

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            // If ADMIN - just set session and return
            if (existingUser.getRole() == User.Role.ADMIN) {
                session.setAttribute("adminId", existingUser.getUserId());
                session.setAttribute("email", existingUser.getEmail());
                session.setAttribute("collegeUid", existingUser.getCollegeUid());
                session.setAttribute("collegeRollNo", existingUser.getCollegeRollNo());
                session.setAttribute("role", existingUser.getRole().name());

                return existingUser;
            }

            //  If USER - just return existing user
            return existingUser;
        }

        // If not in DB - only USER can register
        Contest contest = contestRepository.findByStatusAndAllowedCollegeUidsContaining(
                Contest.Status.ACTIVE,
                userInput.getCollegeUid()
        ).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No active contest found for your college"
        ));

        // Generate new USER ID
        String lastId = userRepository.findLastUserIdForUsers();
        String newId = CustomUserIdGenerator.generateNextUserId(lastId);

        userInput.setUserId(newId);
        userInput.setRole(User.Role.USER);

        return userRepository.save(userInput);
    }

}

