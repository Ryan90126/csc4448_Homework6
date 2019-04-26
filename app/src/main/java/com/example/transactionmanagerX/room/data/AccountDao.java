package com.example.transactionmanagerX.room.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface AccountDao {
    @Query("Select * from Account WHERE user_id=:user_id")
    List<Account> fetchAccounts(Long user_id);

    @Insert
    List<Long> addAccounts(List<Account> accounts);

    @Delete
    void deleteAccount(Account account);
}
