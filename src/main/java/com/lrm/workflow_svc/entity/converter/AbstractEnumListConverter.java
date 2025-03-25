package com.lrm.workflow_svc.entity.converter;

import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractEnumListConverter<T extends Enum<T>> implements AttributeConverter<List<T>, String> {
    private static final String DELIMITER = ",";

    // 让子类实现该方法，返回实际的类型 Class
    protected abstract Class<T> getClazz();


    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return "";
        }
        // 将枚举集合转换为名称字符串，并用逗号连接
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .map(name -> Enum.valueOf(this.getClazz(), name))
                .collect(Collectors.toList());
    }
}
