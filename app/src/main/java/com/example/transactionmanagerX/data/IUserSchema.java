package com.example.transactionmanagerX.data;

public interface IUserSchema {
    String USER_TABLE = "users";
    String COLUMN_ID = "_id";
    String COLUMN_EMAIL = "email";
    String COLUMN_PASSWORD = "password";
    String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + USER_TABLE
            + " ( "
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_EMAIL
            + " TEXT NOT NULL, "
            + COLUMN_PASSWORD
            + " TEXT NOT NULL "
            + ")";

    String[] USER_COLUMNS = new String[] { COLUMN_ID, COLUMN_EMAIL, COLUMN_PASSWORD };
}
