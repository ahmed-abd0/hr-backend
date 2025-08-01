package com.hr.api.module.usermanagement.permission;

import com.hr.api.common.response.PagedResponse;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-07T01:03:06+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class PermissionMapperImpl extends PermissionMapper {

    @Override
    public PermissionDto toDto(Permission permission) {
        if ( permission == null ) {
            return null;
        }

        PermissionDto permissionDto = new PermissionDto();

        permissionDto.setId( permission.getId() );
        permissionDto.setName( permission.getName() );

        return permissionDto;
    }

    @Override
    public List<PermissionDto> toDtos(List<Permission> permission) {
        if ( permission == null ) {
            return null;
        }

        List<PermissionDto> list = new ArrayList<PermissionDto>( permission.size() );
        for ( Permission permission1 : permission ) {
            list.add( toDto( permission1 ) );
        }

        return list;
    }

    @Override
    public Permission toEntity(PermissionDto permission) {
        if ( permission == null ) {
            return null;
        }

        Permission.PermissionBuilder permission1 = Permission.builder();

        permission1.id( permission.getId() );
        permission1.name( permission.getName() );

        return permission1.build();
    }

    @Override
    public PagedResponse<PermissionDto> toPagedDtos(Page<Permission> permissions) {
        if ( permissions == null ) {
            return null;
        }

        PagedResponse<PermissionDto> pagedResponse = new PagedResponse<PermissionDto>();

        if ( permissions.hasContent() ) {
            pagedResponse.setContent( toDtos( permissions.getContent() ) );
        }
        pagedResponse.setLast( permissions.isLast() );
        pagedResponse.setSize( permissions.getSize() );
        pagedResponse.setTotalElements( permissions.getTotalElements() );
        pagedResponse.setTotalPages( permissions.getTotalPages() );

        return pagedResponse;
    }
}
