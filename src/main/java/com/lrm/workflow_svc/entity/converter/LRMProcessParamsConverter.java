package com.lrm.workflow_svc.entity.converter;

import com.lrm.workflow_svc.dto.LRMProcessParams;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class LRMProcessParamsConverter extends AbstractJsonAttributeConverter<LRMProcessParams> {

    @Override
    protected Class<LRMProcessParams> getClazz() {
        return LRMProcessParams.class;
    }
}
