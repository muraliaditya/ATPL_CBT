package com.aaslin.cbt.service;

import com.aaslin.cbt.entity.User;

public interface AuthService {
	User authenticateUserAndJoinContest( User userInput);
}
