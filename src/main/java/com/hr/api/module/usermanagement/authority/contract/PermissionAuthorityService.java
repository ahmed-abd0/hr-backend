package com.hr.api.module.usermanagement.authority.contract;

import com.hr.api.module.usermanagement.permission.Permission;

public interface PermissionAuthorityService {

	boolean existsById(Long permissionId);

	Permission findById(Long permissionId);

	Permission getInstance(Long permissionId);

}
