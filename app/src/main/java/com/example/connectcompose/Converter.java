package com.example.connectcompose;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    @TypeConverter
    public static List<Student> fromString(String value) {
        Type listType = new TypeToken<List<Student>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Student> list) {
        return new Gson().toJson(list);
    }
}