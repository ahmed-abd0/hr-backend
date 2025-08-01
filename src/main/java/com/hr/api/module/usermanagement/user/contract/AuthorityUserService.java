package com.hr.api.module.usermanagement.user.contract;

import com.hr.api.module.usermanagement.authority.Authority;

public interface AuthorityUserService {

	Authority findByName(String name);

	Authority save(Authority role);

	String adjustAuthorityName(String name);

	Authority findById(Long authorityId);
	
	boolean existsById(Long authorityId);

	Authority getInstance(Long roleId);

}
