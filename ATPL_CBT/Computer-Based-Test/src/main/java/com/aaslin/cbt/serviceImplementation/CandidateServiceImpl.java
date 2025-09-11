package com.aaslin.cbt.serviceImplementation;

import org.springframework.stereotype.Service;


//@Service
//public class CandidateServiceImpl implements CandidateService{
//	
//	@Override
//	public ApiResponse<LoginResponseDTO> login(UserLoginDTO loginDTO) {
//		try {
//			Optional<User> user = userRepository.findByEmailAndCollegeUidAndCollegeRollNo(
//				loginDTO.getEmail(), 
//				loginDTO.getCollegeUid(), 
//				loginDTO.getCollegeRollNo()
//			);
//			
//			if (user.isPresent()) {
//				User foundUser = user.get();
//				LoginResponseDTO loginResponse = new LoginResponseDTO();
//				loginResponse.setUserId(foundUser.getUserId());
//				loginResponse.setEmail(foundUser.getEmail());
//				loginResponse.setCollegeUid(foundUser.getCollegeUid());
//				loginResponse.setCollegeRollNo(foundUser.getCollegeRollNo());
//				loginResponse.setRole(foundUser.getRole().toString());
//				
//				return ApiResponse.success("Login successful", loginResponse);
//			} else {
//				return ApiResponse.error("Invalid credentials");
//			}
//		} catch (Exception e) {
//			return ApiResponse.error("Login failed", e.getMessage());
//		}
//	}
//}
