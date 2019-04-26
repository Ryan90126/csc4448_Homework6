/**Adapted from:
 ** Copyright (c) 2010 Ushahidi Inc
 ** All rights reserved
 ** Contact: team@ushahidi.com
 ** Website: http://www.ushahidi.com
 **
 ** GNU Lesser General Public License Usage
 ** This file may be used under the terms of the GNU Lesser
 ** General Public License version 3 as published by the Free Software
 ** Foundation and appearing in the file LICENSE.LGPL included in the
 ** packaging of this file. Please review the following information to
 ** ensure the GNU Lesser General Public License version 3 requirements
 ** will be met: http://www.gnu.org/licenses/lgpl.html.
 **
 **
 ** If you have questions regarding the use of this file, please contact
 ** Ushahidi developers at team@ushahidi.com.
 **
 **/

package com.example.transactionmanagerX.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {

    private static final String TAG = "MyDatabase";
    private static final String DATABASE_NAME = "test_database2.db";
    private DatabaseHelper mDbHelper;
    private int current_user;
    // Increment DB Version on any schema change
    private static final int DATABASE_VERSION = 1;
    private final Context mContext;
    public static UserDao mUserDao;
    public static TransactionDao mTransactionDao;



    public Database open() throws SQLException {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        mUserDao = new UserDao(mDb);
        mTransactionDao = new TransactionDao(mDb);

        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Database(Context context) {
        this.mContext = context;
    }



    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if(!doesVirtualTableExists(db,IUserSchema.USER_TABLE)){
                db.execSQL(IUserSchema.USER_TABLE_CREATE);
            }
            if(!doesVirtualTableExists(db,ITransactionSchema.TRANSACTION_TABLE)){
                db.execSQL(ITransactionSchema.TRANSACTION_TABLE_CREATE);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion) {
            Log.w(TAG, "Upgrading database from version "
                    + oldVersion + " to "
                    + newVersion + " which destroys all old data");
            db.execSQL(IUserSchema.USER_TABLE);
            onCreate(db);

        }
    }


    public static boolean doesVirtualTableExists(SQLiteDatabase db,
                                                 String tableName) {

        Cursor cursor = db
                .rawQuery(
                        String.format(
                                "SELECT DISTINCT tbl_name from sqlite_master where tbl_name ='%s'",
                                tableName), null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }


}
