package com.example.transactionmanagerX.room.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE uid = :userId")
     User fetchUserById(Long userId);

    @Query("SELECT * FROM user WHERE email = :email")
     User fetchUserByEmail(String email);

    @Insert
    List<Long> insertAll(User... users);

    @Delete
    void delete(User user);
}
