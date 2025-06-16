package com.example.backendTeam12.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Validate {
    public static String hashPasswordMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());

            // Chuyển mảng byte thành số nguyên lớn
            BigInteger number = new BigInteger(1, messageDigest);

            // Chuyển thành chuỗi hex
            StringBuilder hashed = new StringBuilder(number.toString(16));

            // Bổ sung số 0 nếu cần để đủ 32 ký tự
            while (hashed.length() < 32) {
                hashed.insert(0, "0");
            }

            return hashed.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 Algorithm not available", e);
        }
    }

    // 2. Kiểm tra số điện thoại (9-11 chữ số)
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("^[0-9]{9,11}$");
    }

    // 3. Kiểm tra định dạng email
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
