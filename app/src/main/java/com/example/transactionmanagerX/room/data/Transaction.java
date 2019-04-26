package com.example.transactionmanagerX.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@SuppressWarnings("unused")

@Entity(tableName = "transaction")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    Long id;

    @ColumnInfo(name = "date")
    Long date;

    @ColumnInfo(name = "payee")
    String payee;

    @ColumnInfo(name = "amount")
    Double amount;

    @ColumnInfo(name = "label_id")
    Long label_id;

    @ColumnInfo(name = "user_id")
    Long user_id;

    @ColumnInfo(name = "account_id")
    Long account_id;

    @Ignore
    public Long getDate() {
        return date;
    }

    @Ignore
    public void setDate(Long date) {
        this.date = date;
    }

    @Ignore
    public String getPayee() {
        return payee;
    }

    @Ignore
    public void setPayee(String payee) {
        this.payee = payee;
    }

    @Ignore
    public Double getAmount() {
        return amount;
    }

    @Ignore
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Ignore
    public Long getAccount_id() {
        return account_id;
    }

    @Ignore
    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
    }






}
