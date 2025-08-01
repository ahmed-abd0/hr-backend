package com.hr.api.module.usermanagement.user;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.auth.request.RegisterRequest;
import com.hr.api.module.usermanagement.authority.Authority;
import com.hr.api.module.usermanagement.authority.AuthorityDto;
import com.hr.api.module.usermanagement.user.request.CreateUserRequest;
import com.hr.api.module.usermanagement.user.request.UpdateUserRequest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-07T00:52:46+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserDto toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.email( user.getEmail() );
        userDto.employeeId( user.getEmployeeId() );
        userDto.enabled( user.isEnabled() );
        userDto.id( user.getId() );
        userDto.name( user.getName() );
        userDto.roles( authoritySetToAuthorityDtoList( user.getRoles() ) );

        return userDto.build();
    }

    @Override
    public User toEntity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.employeeId( dto.getEmployeeId() );
        user.enabled( dto.isEnabled() );
        user.id( dto.getId() );
        user.name( dto.getName() );
        user.roles( authorityDtoListToAuthoritySet( dto.getRoles() ) );

        return user.build();
    }

    @Override
    public User toEntity(CreateUserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.employeeId( dto.getEmployeeId() );
        user.enabled( dto.isEnabled() );
        user.name( dto.getName() );
        user.roles( mapRoles( dto.getRoles() ) );

        return user.build();
    }

    @Override
    public User toEntity(UpdateUserRequest dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( dto.getEmail() );
        user.employeeId( dto.getEmployeeId() );
        user.enabled( dto.isEnabled() );
        user.name( dto.getName() );
        user.roles( mapRoles( dto.getRoles() ) );

        return user.build();
    }

    @Override
    public User toEntity(RegisterRequest registerUserDto) {
        if ( registerUserDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( registerUserDto.email() );
        user.employeeId( registerUserDto.employeeId() );
        user.enabled( registerUserDto.enabled() );
        user.name( registerUserDto.name() );
        user.roles( mapRoles( registerUserDto.roles() ) );

        return user.build();
    }

    @Override
    public List<UserDto> toDtos(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( user.size() );
        for ( User user1 : user ) {
            list.add( toDto( user1 ) );
        }

        return list;
    }

    @Override
    public PagedResponse<UserDto> toPagedDtos(Page<User> user) {
        if ( user == null ) {
            return null;
        }

        PagedResponse<UserDto> pagedResponse = new PagedResponse<UserDto>();

        if ( user.hasContent() ) {
            pagedResponse.setContent( toDtos( user.getContent() ) );
        }
        pagedResponse.setLast( user.isLast() );
        pagedResponse.setSize( user.getSize() );
        pagedResponse.setTotalElements( user.getTotalElements() );
        pagedResponse.setTotalPages( user.getTotalPages() );

        return pagedResponse;
    }

    @Override
    public List<User> toEntites(List<UserDto> user) {
        if ( user == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( user.size() );
        for ( UserDto userDto : user ) {
            list.add( toEntity( userDto ) );
        }

        return list;
    }

    @Override
    public void updateUserFromDto(User user, UserDto userDto) {
        if ( userDto == null ) {
            return;
        }

        if ( userDto.getEmail() != null ) {
            user.setEmail( userDto.getEmail() );
        }
        if ( userDto.getEmployeeId() != null ) {
            user.setEmployeeId( userDto.getEmployeeId() );
        }
        user.setEnabled( userDto.isEnabled() );
        if ( userDto.getId() != null ) {
            user.setId( userDto.getId() );
        }
        if ( userDto.getName() != null ) {
            user.setName( userDto.getName() );
        }
        if ( user.getRoles() != null ) {
            Set<Authority> set = authorityDtoListToAuthoritySet( userDto.getRoles() );
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
        }
        else {
            Set<Authority> set = authorityDtoListToAuthoritySet( userDto.getRoles() );
            if ( set != null ) {
                user.setRoles( set );
            }
        }
    }

    @Override
    public void updateUserFromDto(User user, UpdateUserRequest userDto) {
        if ( userDto == null ) {
            return;
        }

        if ( userDto.getEmail() != null ) {
            user.setEmail( userDto.getEmail() );
        }
        if ( userDto.getEmployeeId() != null ) {
            user.setEmployeeId( userDto.getEmployeeId() );
        }
        user.setEnabled( userDto.isEnabled() );
        if ( userDto.getName() != null ) {
            user.setName( userDto.getName() );
        }
        if ( user.getRoles() != null ) {
            Set<Authority> set = mapRoles( userDto.getRoles() );
            if ( set != null ) {
                user.getRoles().clear();
                user.getRoles().addAll( set );
            }
        }
        else {
            Set<Authority> set = mapRoles( userDto.getRoles() );
            if ( set != null ) {
                user.setRoles( set );
            }
        }
    }

    protected AuthorityDto authorityToAuthorityDto(Authority authority) {
        if ( authority == null ) {
            return null;
        }

        AuthorityDto.AuthorityDtoBuilder authorityDto = AuthorityDto.builder();

        if ( authority.getId() != null ) {
            authorityDto.id( authority.getId() );
        }
        authorityDto.name( authority.getName() );

        return authorityDto.build();
    }

    protected List<AuthorityDto> authoritySetToAuthorityDtoList(Set<Authority> set) {
        if ( set == null ) {
            return null;
        }

        List<AuthorityDto> list = new ArrayList<AuthorityDto>( set.size() );
        for ( Authority authority : set ) {
            list.add( authorityToAuthorityDto( authority ) );
        }

        return list;
    }

    protected Authority authorityDtoToAuthority(AuthorityDto authorityDto) {
        if ( authorityDto == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority = Authority.builder();

        authority.id( authorityDto.getId() );
        authority.name( authorityDto.getName() );

        return authority.build();
    }

    protected Set<Authority> authorityDtoListToAuthoritySet(List<AuthorityDto> list) {
        if ( list == null ) {
            return null;
        }

        Set<Authority> set = new LinkedHashSet<Authority>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( AuthorityDto authorityDto : list ) {
            set.add( authorityDtoToAuthority( authorityDto ) );
        }

        return set;
    }
}
