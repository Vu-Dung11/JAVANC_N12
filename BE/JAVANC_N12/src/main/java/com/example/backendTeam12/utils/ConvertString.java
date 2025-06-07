package com.example.backendTeam12.utils;

import java.text.Normalizer;

public class ConvertString {

    public static String removeVietnameseTones(String str) {
        if (str == null) return null;
        
        str = str.toLowerCase();
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("\\p{M}", "");
        str = str.replaceAll("đ", "d");

        return str;
    }
}
