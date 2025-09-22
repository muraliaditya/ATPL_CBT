package com.aaslin.cbt.developer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aaslin.cbt.developer.Dto.TopDevelopersResponseDto;
import com.aaslin.cbt.developer.service.TopDevelopersService;

@RestController
@RequestMapping("/api/v1/dev/stats")  
public class TopDevelopersController {
	
	@Autowired
    private  TopDevelopersService topDevelopersService;


    @GetMapping
    public ResponseEntity<TopDevelopersResponseDto> getDeveloperStats() {
    	return ResponseEntity.ok(topDevelopersService.getTopDevelopers());
    }
}
