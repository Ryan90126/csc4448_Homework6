package com.example.transactionmanagerX.data;

public interface ILabelScheme {
    String LABEL_TABLE = "labels";
    String COLUMN_ID = "_id";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_NAME = "label_name";
    String LABEL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + LABEL_TABLE
            + " ( "
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_USER_ID
            + " INTEGER NOT NULL, "
            + COLUMN_NAME
            + " TEXT NOT NULL "
            + ")";

    String[] LABEL_COLUMNS = new String[] { COLUMN_ID, COLUMN_NAME, COLUMN_USER_ID};
}

