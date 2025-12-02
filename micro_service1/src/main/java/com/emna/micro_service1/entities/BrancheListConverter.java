package com.emna.micro_service1.entities;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class BrancheListConverter implements AttributeConverter<List<Branche>, String> {

    @Override
    public String convertToDatabaseColumn(List<Branche> attribute) {
        if (attribute == null || attribute.isEmpty()) return "";
        return attribute.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public List<Branche> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return List.of();
        return Arrays.stream(dbData.split(","))
                .map(Branche::valueOf)
                .collect(Collectors.toList());
    }
}
