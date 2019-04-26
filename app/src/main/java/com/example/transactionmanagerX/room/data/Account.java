package com.example.transactionmanagerX.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Account {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "number")
    public String number;
    @ColumnInfo(name = "institution")
    public String institution;
    @ColumnInfo(name = "last_sync")
    public Long last_sync;
    @ColumnInfo(name = "login")
    public String login;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "user_id")
    public Long user_id;

}