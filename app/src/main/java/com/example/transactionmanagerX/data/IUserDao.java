package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.User;

import java.util.List;

public interface IUserDao {
    public User fetchUserById(int userId);
    public List<User> fetchAllUsers();
    // add user
    public boolean addUser(User user);
    // add users in bulk
    public boolean addUsers(List<User> users);
    public boolean deleteAllUsers();
    public User fetchUserByEmail(String email);
}
