package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.Account;

import java.util.List;

public interface IAccountDao {
    List<Account> fetchAccounts(int user_id);
    boolean addAccount(Account account);
    boolean addAccounts(List<Account> accounts);
    boolean deleteAccount(Account account);
}
