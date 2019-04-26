package com.example.transactionmanagerX.room.external;

import com.example.transactionmanagerX.room.data.Account;
import com.example.transactionmanagerX.room.data.Transaction;

import java.util.List;

public abstract class BankConnection {
    public static String NAME;
    abstract BankConnection newInstance();
    public abstract int connect(String login, String password);
    public abstract List<Account> getAccountInfo();
    public abstract List<Transaction> syncTransactions(Account account);
    public abstract void close();
}
