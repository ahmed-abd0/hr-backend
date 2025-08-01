package com.hr.api.module.usermanagement.authority;

import com.hr.api.common.response.PagedResponse;
import com.hr.api.module.usermanagement.authority.request.CreateAuthorityRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-07T01:05:12+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class AuthorityMapperImpl extends AuthorityMapper {

    @Override
    public AuthorityDto toDto(Authority authority) {
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

    @Override
    public Authority toEntity(CreateAuthorityRequest authority) {
        if ( authority == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority1 = Authority.builder();

        authority1.name( mapName( authority.getName() ) );
        authority1.permissions( mapPermissions( authority.getPermissions() ) );

        return authority1.build();
    }

    @Override
    public Authority toEntity(AuthorityDto authority) {
        if ( authority == null ) {
            return null;
        }

        Authority.AuthorityBuilder authority1 = Authority.builder();

        authority1.name( mapName( authority.getName() ) );
        authority1.id( authority.getId() );

        return authority1.build();
    }

    @Override
    public List<AuthorityDto> toDtos(List<Authority> authorities) {
        if ( authorities == null ) {
            return null;
        }

        List<AuthorityDto> list = new ArrayList<AuthorityDto>( authorities.size() );
        for ( Authority authority : authorities ) {
            list.add( toDto( authority ) );
        }

        return list;
    }

    @Override
    public PagedResponse<AuthorityDto> toPagedDtos(Page<Authority> authorities) {
        if ( authorities == null ) {
            return null;
        }

        PagedResponse<AuthorityDto> pagedResponse = new PagedResponse<AuthorityDto>();

        if ( authorities.hasContent() ) {
            pagedResponse.setContent( toDtos( authorities.getContent() ) );
        }
        pagedResponse.setLast( authorities.isLast() );
        pagedResponse.setSize( authorities.getSize() );
        pagedResponse.setTotalElements( authorities.getTotalElements() );
        pagedResponse.setTotalPages( authorities.getTotalPages() );

        return pagedResponse;
    }
}
