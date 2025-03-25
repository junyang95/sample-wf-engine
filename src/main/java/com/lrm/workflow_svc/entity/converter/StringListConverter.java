package com.lrm.workflow_svc.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter(autoApply = false)
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return "";
        }


        return String.join(DELIMITER, strings);
    }

    @Override
    public List<String> convertToEntityAttribute(String s) {
        if (s == null || s.trim().isEmpty()) {
            return List.of();
        }

        return List.of(s.split(DELIMITER));
    }
}
