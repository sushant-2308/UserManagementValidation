package com.geekster.UserManagementValidation.controller;


import com.geekster.UserManagementValidation.Util.UserUtil;
import com.geekster.UserManagementValidation.model.UserManagement;
import com.geekster.UserManagementValidation.service.UserService;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

      @Autowired
      UserService userService;

    @GetMapping(value = "/getAllUsers")
    public List<UserManagement> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping(value = "/addUser")
    public String addUser(@RequestBody UserManagement userManagement)
    {
        JSONObject json=new JSONObject(userManagement);
        List<String> validationList = UserUtil.userValidation(json);
        if (validationList.isEmpty()) {
            UserManagement user = UserUtil.setUser(json);
            userService.addMyUser(user);
            return "User Saved Successfully";
        } else {
            return validationList.toString();
        }
    }

    @GetMapping(value = "/getUserId/{id}")
    public UserManagement getUserById(@PathVariable String id){
        return userService.getUserBasedId(id);
    }

    @PutMapping(value = "/updateUser/{id}")
    public String updateUser(@PathVariable int id,@RequestBody UserManagement userManagement){
        return userService.updateUser(id,userManagement);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public String deleteUserById(@PathVariable String id){
        return userService.removeUserId(id);
    }
}
