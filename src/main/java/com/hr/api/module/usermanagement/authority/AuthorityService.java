package com.hr.api.module.usermanagement.authority;


import java.lang.classfile.instruction.ReturnInstruction;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.authority.request.CreateAuthorityRequest;
import com.hr.api.module.usermanagement.permission.Permission;
import com.hr.api.module.usermanagement.permission.PermissionDto;
import com.hr.api.module.usermanagement.user.User;
import com.hr.api.module.usermanagement.user.contract.AuthorityUserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorityService implements AuthorityUserService {

	private final AuthorityRepository authorityRepository;
	
	private final AuthorityMapper authorityMapper;
	
	
	public Authority create(Authority authority) {
		
		return this.authorityRepository.save(authority);
	}
	
	
	@Transactional
	public Authority giveAuthorityToUsers(String name, Set<User> users) {
		
		Authority authority = getOrCreate(name, users);
		
		users.stream().forEach(user -> {
			authority.getUsers().add(user);
			user.getRoles().add(authority);
		});
		
		return authorityRepository.save(authority);
	}
	
	
	public Authority giveAuthorityToUser(String name, User user) {
		
		return this.giveAuthorityToUsers(name, new HashSet<>(List.of(user)));
	}
	

	
	private Authority getOrCreate(String name, Set<User> users) {

		return authorityRepository.findByName(name)
		        .orElseGet(() -> create( Authority.getInstance(name, users)));
	}


	public Authority findByName(String name) {

		return this.authorityRepository.findByName(name).orElseThrow(() -> new RuntimeException("Role " + name + " not found"));
	}


	public AuthorityDto createAuthority(CreateAuthorityRequest request) {

		if(authorityRepository.existsByName(Authority.adjustAuthorityName(request.getName()))) {
			throw new RuntimeException("Authority name already exists");
		}
		
		Authority authority = this.authorityRepository.save(authorityMapper.toEntity(request));
		
		return this.authorityMapper.toDto(authority);
	}


	public void deleteAuthority(Long id) {
		
		this.authorityRepository.deleteById(id);
	}


	

	public PagedResponse<AuthorityDto> findAllDtos(Pageable pageable) {

		return this.authorityMapper.toPagedDtos(this.authorityRepository.findAll(pageable));
	}


	@Override
	public Authority save(Authority authority) {
		return this.authorityRepository.save(authority);
	}


	@Override
	public String adjustAuthorityName(String name) {
		return Authority.adjustAuthorityName(name);
	}


	@Override
	public Authority findById(Long authorityId) {
		
		return this.authorityRepository.findById(authorityId).get();
	}


	@Override
	public boolean existsById(Long authorityId) {
		
		return this.authorityRepository.existsById(authorityId);
	}


	@Override
	public Authority getInstance(Long roleId) {
		
		return Authority.getInstance(roleId);
	}


	public Set<Permission> getPermissionsForAuthority(long id) {

		return authorityRepository.findById(id).get().getPermissions();
	}
	
	
}





