package com.lrm.workflow_svc.entity.converter;

import com.lrm.workflow_svc.enums.LRMRole;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LRMRoleListConverter extends AbstractEnumListConverter<LRMRole>{

    @Override
    protected Class<LRMRole> getClazz() {
        return LRMRole.class;
    }
}
