package com.aaslin.cbt.super_admin.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.aaslin.cbt.common.model.Contest;

import jakarta.persistence.criteria.Predicate;

public class ContestSpecifications {

    public static Specification<Contest> filterByNameAndStatus(String contestName, String status) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (contestName != null && !contestName.trim().isEmpty()) {
           
                predicates.add(cb.like(cb.lower(root.get("contestName")), "%" + contestName.trim().toLowerCase() + "%"));
            }

            if (status != null && !status.trim().isEmpty() && !"ALL".equalsIgnoreCase(status)) {
                
                if ("ACTIVE".equalsIgnoreCase(status)) {
                    predicates.add(cb.equal(root.get("isActive"), true));
                } else if ("COMPLETED".equalsIgnoreCase(status) || "INACTIVE".equalsIgnoreCase(status)) {
                    predicates.add(cb.equal(root.get("isActive"), false));
                } else {
                    predicates.add(cb.equal(cb.upper(root.get("status")), status.trim().toUpperCase()));
                }
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
