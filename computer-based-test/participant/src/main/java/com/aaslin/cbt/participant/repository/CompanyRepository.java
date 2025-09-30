package com.aaslin.cbt.participant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Company;

public interface CompanyRepository extends JpaRepository<Company,String>{
	List<Company> findBycurrentCompanyName(String currentCompanyName);

}
