package com.example.transactionmanagerX.data;


import com.example.transactionmanagerX.data.primitives.Transaction;

import java.util.List;

public interface ITransactionDao {
    public boolean addTransaction(Transaction transaction);
    public boolean deleteTransaction(int id);
    public List<Transaction> getTransactionsBatch(int user_id, Long start, int count);
    public List<Transaction> getTransactionsByLabel(int user_id, Long start, int label_id, int count);
    public Double getTransactionSum(int user_id, Long end);
    public Double getLabelSum(int user_id, Long end, int label_id);
    public int setTransactionsLabel(List<Integer> transaction_ids, int label_id);
    public List<Transaction> loadTransactions(int user_id, Long end);

}
