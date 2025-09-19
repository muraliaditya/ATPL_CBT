package com.aaslin.cbt.participant.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aaslin.cbt.common.model.Company;

public interface CompanyRepository extends JpaRepository<Company,String>{
	Optional<Company> findBycurrentCompanyName(String currentCompanyName);

}
