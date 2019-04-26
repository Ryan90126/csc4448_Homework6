package com.example.transactionmanagerX.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.transactionmanagerX.data.primitives.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDao extends DbContentProvider implements IAccountDao, IAccountSchema {

    private Cursor cursor;
    private ContentValues initialValues;
    private DateMath dateMath;

    public AccountDao(SQLiteDatabase db){super(db);}

    private void setContentValue(Account account) {
        initialValues = new ContentValues();
        dateMath=new DateMath();
        initialValues.put(COLUMN_ID, account.id);
        initialValues.put(COLUMN_INSTITUTION_NAME, account.institution);
        initialValues.put(COLUMN_LAST_SYNC, account.last_sync);
        initialValues.put(COLUMN_LOGIN, account.id);
        initialValues.put(COLUMN_USER_ID, account.user_id);
        initialValues.put(COLUMN_NUMBER, account.number);
        initialValues.put(COLUMN_PASSWORD, account.password);
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    @Override
    protected Account cursorToEntity(Cursor cursor) {
        Account account = new Account();

        int idIndex;
        int syncIndex;
        int numberIndex;
        int instIndex;
        int loginIndex;
        int userIndex;
        int passIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                account.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_NUMBER) != -1) {
                numberIndex = cursor.getColumnIndexOrThrow(COLUMN_NUMBER);
                account.number = cursor.getString(numberIndex);
            }
            if (cursor.getColumnIndex(COLUMN_LAST_SYNC) != -1) {
                syncIndex = cursor.getColumnIndexOrThrow(COLUMN_LAST_SYNC);
                account.last_sync = cursor.getLong(syncIndex);
            }
            if (cursor.getColumnIndex(COLUMN_INSTITUTION_NAME) != -1) {
                instIndex = cursor.getColumnIndexOrThrow(COLUMN_INSTITUTION_NAME);
                account.institution = cursor.getString(instIndex);
            }
            if (cursor.getColumnIndex(COLUMN_USER_ID) != -1) {
                userIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_ID);
                account.user_id = cursor.getInt(userIndex);
            }
            if (cursor.getColumnIndex(COLUMN_LOGIN) != -1) {
                loginIndex = cursor.getColumnIndexOrThrow(COLUMN_LOGIN);
                account.login = cursor.getString(loginIndex);
            }
            if (cursor.getColumnIndex(COLUMN_PASSWORD) != -1) {
                passIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);
                account.password = cursor.getString(passIndex);
            }


        }
        return account;
    }

    @Override
    public List<Account> fetchAccounts(int user_id) {
        List<Account> account = new ArrayList<>();
        final String selectionArgs[] = {String.valueOf(user_id)};
        final String selection = COLUMN_USER_ID +" = ? ";
        cursor = super.query(ACCOUNT_TABLE,ACCOUNT_COLUMNS,selection,selectionArgs, COLUMN_ID + " ASC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                account.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return account;
    }

    @Override
    public boolean addAccount(Account account) {
        initialValues = new ContentValues();
        setContentValue(account);
        try {
            return super.insert(ACCOUNT_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean addAccounts(List<Account> accounts) {
        try{
            mDb.beginTransaction();

            for (Account account : accounts) {
                addAccount(account);
            }

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

    @Override
    public boolean deleteAccount(Account account) {
        final String selectionArgs[] = { String.valueOf(account.id) };
        final String selection = COLUMN_ID + " = ?";
        try {
            return super.delete(ACCOUNT_TABLE,selection,selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }
}
