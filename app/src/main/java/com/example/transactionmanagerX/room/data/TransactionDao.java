package com.example.transactionmanagerX.room.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
interface TransactionDao {
    @Insert
    List<Long> addTransactions(Transaction... transactions);

    @Insert
    List<Long> addTransactionList(List<Transaction> transactions);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Update
    int updateTransactions (Transaction... transactions);

    @Query("SELECT * FROM `transaction` WHERE user_id = :user_id ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactions(Long user_id);

    @Query("SELECT * FROM `transaction` WHERE user_id = :user_id AND label_id = :label_id ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactionsByLabel(Long user_id, Long label_id);

    @Query("SELECT * FROM `transaction`WHERE user_id = :user_id AND date > :end ORDER BY date DESC")
    LiveData<List<Transaction>> getTransactionSinceDate(Long user_id, Long end);

    @Query("SELECT * FROM `transaction` WHERE user_id = :user_id AND label_id =:label_id and date>:end ORDER BY date DESC")
    LiveData<List<Transaction>> getLabeledTransactionsSinceDate(Long user_id, Long end, Long label_id);

    @Query("UPDATE `transaction` SET label_id = :label_id WHERE id IN (:transaction_ids)")
    int setTransactionsLabel(List<Integer> transaction_ids, Long label_id);

}
