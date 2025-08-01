package com.hr.api.module.usermanagement.auth.contract;

import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.dto.UserDto;

public interface UserAuthService {

	User register(RegisterRequest registerRequest);

	UserDto getDtoByEmail(String email);

}
