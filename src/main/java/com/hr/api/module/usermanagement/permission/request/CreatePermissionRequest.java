package com.hr.api.module.usermanagement.permission.request;



import com.hr.api.common.annotation.Unique;
import com.hr.api.module.usermanagement.permission.Permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePermissionRequest {
  
	@NotBlank()
	@Unique(entity = Permission.class, fieldName = "name")
    private String name;
	
}
