package com.hr.api.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class SchemaTenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    private static final String DEFAULT_TENANT_ID = "default";

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getTenantId();
        return (tenantId != null) ? tenantId : DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}