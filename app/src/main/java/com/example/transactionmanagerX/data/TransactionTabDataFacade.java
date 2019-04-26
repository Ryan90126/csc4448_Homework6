package com.example.transactionmanagerX.data;

import android.content.Context;

import com.example.transactionmanagerX.data.primitives.Transaction;

import java.util.List;

public class TransactionTabDataFacade extends Database {
    private DateMath dateMath;
    private int user_id;
    private Long oldestDate;
    public TransactionTabDataFacade(Context context) {
        super(context);
        dateMath = new DateMath();
    }

    public void setUserID(int id){
        this.user_id=id;
    }

    public List<Transaction> loadTransactions(){
        List<Transaction> temp = mTransactionDao.loadTransactions(user_id,dateMath.getSomeDaysAgo(30));
        oldestDate=temp.get(temp.size()-1).datetime;
        return temp;
    }

    public List<Transaction> getMoreTransactions(){
        List<Transaction> temp = mTransactionDao.getTransactionsBatch(user_id,oldestDate,30);
        oldestDate=temp.get(temp.size()-1).datetime;
        return temp;
    }

    public boolean generateATransaction(){
        Transaction temp = new Transaction();
        temp.amount=10.00;
        temp.datetime=dateMath.getNow();
        temp.label_id=0;
        temp.payee="Test";
        temp.user_id=this.user_id;
        return mTransactionDao.addTransaction(temp);
    }

    public boolean updateLabels(List<Integer> transaction_ids, int label_id){
        return mTransactionDao.setTransactionsLabel(transaction_ids, label_id)>0;
    }
}
