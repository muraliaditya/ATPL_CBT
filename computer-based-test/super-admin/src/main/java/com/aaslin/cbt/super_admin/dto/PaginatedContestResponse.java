package com.aaslin.cbt.super_admin.dto;

import java.util.List;

import lombok.*;

@Data
public class PaginatedContestResponse {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private List<ContestDTO> contests;

    public PaginatedContestResponse(int page, int size, long totalElements, int totalPages, List<ContestDTO> contests) {
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.contests = contests;
    }
}

