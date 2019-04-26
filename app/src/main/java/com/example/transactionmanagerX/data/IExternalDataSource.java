package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.Account;
import com.example.transactionmanagerX.data.primitives.Transaction;

import java.util.List;

public interface IExternalDataSource {
     List<Account> getAccounts(String credentials);
     List<Transaction> getAccountTransactions(Account account);
     boolean open();
     boolean close();
     IExternalDataSource newInstance();
}
