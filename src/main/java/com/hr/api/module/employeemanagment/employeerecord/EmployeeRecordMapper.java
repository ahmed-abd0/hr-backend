package com.hr.api.module.employeemanagment.employeerecord;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.employeerecord.dto.EmployeeRecordDto;

@Mapper(componentModel = "spring")
public abstract class EmployeeRecordMapper {
	
	
	public abstract EmployeeRecordDto toDto(EmployeeRecord record);

	public abstract List<EmployeeRecordDto> toDtos(List<EmployeeRecord> byEmployeeId);

	public abstract PagedResponse<EmployeeRecordDto> toPagedDto(Page<EmployeeRecord> byEmployeeId);
}
