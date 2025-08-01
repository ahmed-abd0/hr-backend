package com.hr.api.multitenancy.tenantmanagment;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-07-06T23:01:53+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.39.0.v20240820-0604, environment: Java 21.0.4 (Eclipse Adoptium)"
)
@Component
public class TenantMapperImpl implements TenantMapper {

    @Override
    public DataSourceConfig toEntity(CreateTenantRequest dto) {
        if ( dto == null ) {
            return null;
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig();

        dataSourceConfig.setDriverClassName( dto.getDriverClassName() );
        dataSourceConfig.setPassword( dto.getPassword() );
        dataSourceConfig.setTenantId( dto.getTenantId() );
        dataSourceConfig.setUrl( dto.getUrl() );
        dataSourceConfig.setUsername( dto.getUsername() );

        return dataSourceConfig;
    }
}
