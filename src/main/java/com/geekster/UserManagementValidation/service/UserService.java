package com.geekster.UserManagementValidation.service;

import com.geekster.UserManagementValidation.model.UserManagement;
import com.geekster.UserManagementValidation.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public List<UserManagement> getAllUsers(){
        return userDao.getUsersFromDataSource();
    }

    public String addMyUser(UserManagement userManagement)
    {
        boolean insertionStatus=userDao.save(userManagement);
        if(insertionStatus)
            return "User added successfully..!";
        else
            return "Failed user add not possible";
    }

    public UserManagement getUserBasedId(String id)
    {
        List<UserManagement> UserRightNow=userDao.getUsersFromDataSource();

        for(UserManagement userManagement:UserRightNow){
            if(userManagement.getUserId()==Integer.parseInt(id))
                return userManagement;
        }
        return null;
    }

    public String updateUser(int id,UserManagement userManagement) {
        List<UserManagement> UserRightNow=userDao.getUsersFromDataSource();
        for(UserManagement userManage:UserRightNow){
            if(userManage.getUserId()==(id)) {
                userManage.setUserName(userManagement.getUserName());
                userManage.setDateOfBirth(userManagement.getDateOfBirth());
                userManage.setEmail(userManagement.getEmail());
                userManage.setPhoneNumber(userManagement.getPhoneNumber());
                userManage.setDate(LocalDate.now());
                userManage.setTime(LocalTime.now());
                return "user updates successfully";
            }
        }
        return "cannot update User";
    }

    public String removeUserId(String id)
    {
        boolean deleteResponse=false;
        String status;
        if(id!=null){
            List<UserManagement> UserRightNow=userDao.getUsersFromDataSource();

            for(UserManagement userManagement:UserRightNow){
                if(userManagement.getUserId()==Integer.parseInt(id)) {
                    deleteResponse=userDao.remove(userManagement);
                    if(deleteResponse)
                    {
                        status="User with id "+id+" was deleted";
                    }
                    else{
                        status="Deletion was not possible on  "+id;
                    }
                    return status;
                }
            }
            return "User with "+id+" does not exist";
        }
        return "invalid id cannot acept null id";
    }
}
