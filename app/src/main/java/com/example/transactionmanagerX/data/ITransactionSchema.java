package com.example.transactionmanagerX.data;

public interface ITransactionSchema {
    String TRANSACTION_TABLE = "transactions";
    String COLUMN_ID = "_id";
    String COLUMN_DATE = "date";
    String COLUMN_PAYEE = "payee";
    String COLUMN_AMOUNT = "amount";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_ACCOUNT_ID = "account_id";
    String COLUMN_LABEL_ID = "label_id";
    String TRANSACTION_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TRANSACTION_TABLE
            + " ( "
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_DATE
            + " INTEGER NOT NULL, "
            + COLUMN_PAYEE
            + " TEXT, "
            + COLUMN_AMOUNT
            + " REAL NOT NULL, "
            + COLUMN_LABEL_ID
            + " INTEGER, "
            + COLUMN_USER_ID
            + " INTEGER NOT NULL, "
            + COLUMN_ACCOUNT_ID
            + " INTEGER "
            + ")";

    String[] TRANSACTION_COLUMNS = new String[] { COLUMN_ID , COLUMN_DATE , COLUMN_PAYEE, COLUMN_AMOUNT , COLUMN_USER_ID, COLUMN_ACCOUNT_ID , COLUMN_LABEL_ID };
}
