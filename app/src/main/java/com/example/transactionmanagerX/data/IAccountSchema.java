package com.example.transactionmanagerX.data;

public interface IAccountSchema {
    String ACCOUNT_TABLE = "accounts";
    String COLUMN_ID = "_id";
    String COLUMN_NAME = "name";
    String COLUMN_NUMBER = "number";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_INSTITUTION_NAME = "institution_name";
    String COLUMN_LOGIN = "login";
    String COLUMN_PASSWORD = "password";
    String COLUMN_LAST_SYNC = "last_sync";
    String ACCOUNT_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + ACCOUNT_TABLE
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_NUMBER
            + " TEXT NOT NULL, "
            + COLUMN_LOGIN
            + " TEXT NOT NULL, "
            + COLUMN_PASSWORD
            + " TEXT NOT NULL, "
            + COLUMN_USER_ID
            + " INTEGER NOT NULL, "
            + COLUMN_LAST_SYNC
            + " LONG NOT NULL, "
            + COLUMN_INSTITUTION_NAME
            + " TEXT NOT NULL "
            + ")";

    String[] ACCOUNT_COLUMNS = new String[] { COLUMN_ID,
            COLUMN_NAME, COLUMN_NUMBER, COLUMN_USER_ID, COLUMN_LAST_SYNC, COLUMN_INSTITUTION_NAME };
}
