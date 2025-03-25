package com.lrm.workflow_svc.entity.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;

public abstract class AbstractJsonAttributeConverter<T> implements AttributeConverter<T, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 让子类实现该方法，返回实际的类型 Class
    protected abstract Class<T> getClazz();

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            // 将对象转换为 JSON 字符串
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            // 出现异常时抛出运行时异常，你也可以根据需求进行处理或记录日志
            throw new IllegalArgumentException("Error converting object to JSON", e);
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        try {
            // 将 JSON 字符串转换为对象
            return objectMapper.readValue(dbData, this.getClazz());
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to object", e);
        }
    }
}
