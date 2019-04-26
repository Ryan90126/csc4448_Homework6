package com.example.transactionmanagerX.data;

public interface IVisualScheme {
    String VISUAL_TABLE = "accounts";
    String TYPE_SUM = "0";
    String TYPE_PIE = "1";
    String TYPE_LINE = "2";
    String COLUMN_ID = "_id";
    String COLUMN_NAME = "name";
    String COLUMN_USER_ID = "user_id";
    String COLUMN_LABEL_IDS = "label_ids";
    String COLUMN_TYPE = "type";
    String COLUMN_RANGE = "range";
    String VISUAL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + VISUAL_TABLE
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME
            + " TEXT NOT NULL, "
            + COLUMN_LABEL_IDS
            + " TEXT NOT NULL, "
            + COLUMN_TYPE
            + " INTEGER NOT NULL, "
            + COLUMN_RANGE
            + " INTEGER NOT NULL, "
            + COLUMN_USER_ID
            + " INTEGER NOT NULL "
            + ")";

    String[] VISUAL_COLUMNS = new String[] { COLUMN_ID,
            COLUMN_NAME, COLUMN_LABEL_IDS, COLUMN_USER_ID, COLUMN_TYPE, COLUMN_RANGE };
}
