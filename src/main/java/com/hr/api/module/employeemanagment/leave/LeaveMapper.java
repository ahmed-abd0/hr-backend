package com.hr.api.module.employeemanagment.leave;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.leave.dto.LeaveDto;
import com.hr.api.module.employeemanagment.leave.request.CreateLeaveRequest;
import com.hr.api.module.employeemanagment.leave.request.UpdateLeaveRequest;

@Mapper(componentModel = "spring")
abstract public class LeaveMapper {

	
    @Mapping(source = "employee.id", target = "employeeId")
    public abstract LeaveDto toDto(Leave leave);

    public abstract List<LeaveDto> toDtos(List<Leave> leaves);

	public abstract PagedResponse<LeaveDto> toPagedDto(Page<Leave> leaves);

    
    public abstract Leave toEntity(CreateLeaveRequest request);
	
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	public abstract void updateLeaveFromRequest(UpdateLeaveRequest updateLeaveRequest, @MappingTarget Leave leave);

}