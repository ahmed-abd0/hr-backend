package com.hr.api.module.employeemanagment.leave;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.employeemanagment.employee.Employee;
import com.hr.api.module.employeemanagment.employee.EmployeeRepository;
import com.hr.api.module.employeemanagment.leave.dto.LeaveDto;
import com.hr.api.module.employeemanagment.leave.request.CreateLeaveRequest;
import com.hr.api.module.employeemanagment.leave.request.UpdateLeaveRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRepository leaveRepo;
    private final EmployeeRepository employeeRepo;
    private final LeaveMapper leaveMapper;


	public PagedResponse<LeaveDto> getAll(Pageable pageable) {

		return leaveMapper.toPagedDto(leaveRepo.findAll(pageable));
	}
    
    public LeaveDto create(CreateLeaveRequest request) {
     
    	Employee employee = employeeRepo.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

    	Leave leave = leaveMapper.toEntity(request);
    	
    	leave.setEmployee(employee);
    			
        return leaveMapper.toDto(leaveRepo.save(leave));
    }

    public LeaveDto update(Long id, UpdateLeaveRequest request) {
     
    	Leave leave = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found"));

    	leaveMapper.updateLeaveFromRequest(request, leave);

        return leaveMapper.toDto(leaveRepo.save(leave));
    }

    public List<LeaveDto> getByEmployee(Long employeeId) {
    	
        return leaveMapper.toDtos(leaveRepo.findByEmployeeId(employeeId));
    }

    public void delete(Long id) {
       
    	leaveRepo.deleteById(id);
    }

}