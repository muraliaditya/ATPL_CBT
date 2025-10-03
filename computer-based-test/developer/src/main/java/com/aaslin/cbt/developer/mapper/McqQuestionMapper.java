package com.aaslin.cbt.developer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aaslin.cbt.common.model.McqQuestion;
import com.aaslin.cbt.developer.Dto.AddMcqQuestionRequestDto;
@Mapper(componentModel = "spring")
public interface McqQuestionMapper {

    @Mapping(target = "mcqQuestionId", ignore = true) 
    @Mapping(target = "questionText", source = "question")
    @Mapping(target = "section", ignore = true)     
    @Mapping(target = "isActive", expression = "java(dto.getIsActive() != null ? dto.getIsActive() : true)")
    @Mapping(target = "weightage", expression = "java(dto.getWeightage() != null ? dto.getWeightage() : 1)")
    @Mapping(target = "approvalStatus", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "approvedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    McqQuestion toEntity(AddMcqQuestionRequestDto.McqQuestionDto mcqQuestionDto);
}
