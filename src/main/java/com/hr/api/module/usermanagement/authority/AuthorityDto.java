package com.hr.api.module.usermanagement.authority;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorityDto {

	private long id;
	
	private String name;
	
}
