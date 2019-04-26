package com.example.transactionmanagerX.room.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class VisualData {
    public static int TYPE_SUM = 0;
    public static int TYPE_PIE = 1;
    public static int TYPE_LINE = 2;
    @PrimaryKey(autoGenerate = true)
    public Long id;
    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "label_ids")
    public String label_ids;
    @ColumnInfo(name = "range")
    public int range;
    @ColumnInfo(name = "graphType")
    public int graphType;
    @ColumnInfo(name = "user_id")
    public int user_id;
}
