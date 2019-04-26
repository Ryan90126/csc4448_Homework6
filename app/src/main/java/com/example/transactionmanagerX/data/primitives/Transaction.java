package com.example.transactionmanagerX.data.primitives;

import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;

public class Transaction implements Comparable<Transaction> {
    public int id;
    public String payee;
    public Double amount;
    public int label_id;
    public Long datetime;
    public int account_id;
    public int user_id;
    private static final SimpleDateFormat monthDayYear = new SimpleDateFormat("MMM-dd-yy");
    private static final SimpleDateFormat monthDay = new SimpleDateFormat("MMM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public Transaction() {};
    public Transaction(String payee, Double amount, Long datetime){
        this.payee=payee;
        this.amount=amount;
        this.datetime=datetime;
        this.label_id=0;
    }

    public String getDate(){
        return monthDay.format(this.datetime);
    }

    public String getDateWithYear(){
        return monthDayYear.format(this.datetime);
    }

    public String getTime() {
        return timeFormat.format(this.datetime);
    }

    public void setDatetime(Long datetime) {
        this.datetime=datetime;
    }

    public Double getAmount(){
        return this.amount;
    }

    public String getPayee(){
        return this.payee;
    }
    public Long getDateTime() {
        return this.datetime;
    }
    @Override
    public int compareTo(@NonNull Transaction transaction) {
        return getDateTime().compareTo(transaction.getDateTime());
    }
}

