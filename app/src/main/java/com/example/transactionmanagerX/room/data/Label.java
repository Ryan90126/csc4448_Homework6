package com.example.transactionmanagerX.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Label {
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "color")
    public Long color;
    @ColumnInfo(name = "user_id")
    public Long user_id;
}
