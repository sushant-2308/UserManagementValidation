package com.geekster.UserManagementValidation.Util;

import com.geekster.UserManagementValidation.model.UserManagement;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserUtil {

    public static List<String> userValidation(JSONObject json) {
        List<String> errors = new ArrayList<>();

        if (json.getString("userName").length() == 0) {
            errors.add("USERNAME:- Username is Mandatory");
        }
        if (!json.has("dateOfBirth")) {
            errors.add("DATEOFBIRTH:- DateOfBirth is Mandatory");
        } else if (json.getString("dateOfBirth").charAt(2) != '-' || json.getString("dateOfBirth").charAt(5) != '-' || json.getString("dateOfBirth").length() != 10) {
            errors.add("DATEOFBIRTH:- Wrong Date Format");
        }
        if (json.getString("email").length() == 0) {
            errors.add("EMAIL:- Email is Mandatory");
        } else if (!UserUtil.validEmail(json.getString("email"))) {
            errors.add("EMAIL:- Email is incorrect. Please provide a valid email");
        }
        if ((!json.has("phoneNumber")) || (json.getString("phoneNumber").length() != 12) || (json.getString("phoneNumber").charAt(0) != '9') || (json.getString("phoneNumber").charAt(1) != '1')) {
            errors.add("PHONENUMBER:- Incorrect phone number less than 12 digits it should start with counrtry code (91)");
        } else if (!validPhoneNumber(json.getString("phoneNumber").substring(2, 12))) {
            errors.add("PHONENUMBER:- Not a valid Phone number");
        }
        return errors;
    }

    public static boolean validPhoneNumber(String phoneNumber) {
        Pattern p = Pattern.compile("^\\d{10}$");
        Matcher m = p.matcher(phoneNumber);
        return (m.matches());
    }

    private static boolean validEmail(String email) {
        String reg = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(email);
        boolean result = (matcher.matches() ? true : false);
        return result;
    }

    public static UserManagement setUser(JSONObject json) {
        UserManagement users = new UserManagement();
        users.setUserId(json.getInt("userId"));
        users.setUserName(json.getString("userName"));
        users.setDateOfBirth(json.getString("dateOfBirth"));
        users.setEmail(json.getString("email"));
        users.setPhoneNumber(json.getString("phoneNumber"));
        LocalTime currTime = LocalTime.now();
        users.setTime(currTime);
        LocalDate currDate = LocalDate.now();
        users.setDate(currDate);
        return users;
    }
}
