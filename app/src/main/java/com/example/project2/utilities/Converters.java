package com.example.project2.utilities;

/**
 *  Information on type converters
 *  and how to properly do this was
 *  found online through various resources
 *  -Austin
 */

import androidx.room.TypeConverter;

import com.example.project2.ElementalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public String fromElementalTypeList(List<ElementalType> elements) {
        return elements.stream().map(Enum::name).collect(Collectors.joining(","));
    }

    @TypeConverter
    public List<ElementalType> toElementalTypeList(String data) {
        if (data == null || data.isEmpty()) return new ArrayList<>();

        return Arrays.stream(data.split(",")).map(ElementalType::valueOf).collect(Collectors.toList());
    }

    @TypeConverter
    public String fromStringList(List<String> list) {
        return String.join(",", list);
    }

    @TypeConverter
    public List<String> toStringList(String data) {
        if (data == null || data.isEmpty()) return new ArrayList<>();

        return Arrays.asList(data.split(","));
    }
}
