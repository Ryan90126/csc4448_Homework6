package com.example.transactionmanagerX.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.transactionmanagerX.data.primitives.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao extends DbContentProvider implements IUserSchema, IUserDao {
    private Cursor cursor;
    private ContentValues initialValues;
    public UserDao(SQLiteDatabase db) {
        super(db);
    }
    public User fetchUserById(int id) {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUMN_ID + " = ?";
        User user = new User();
        cursor = super.query(USER_TABLE, USER_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return user;
    }

    public List<User> fetchAllUsers() {
        List<User> userList = new ArrayList<User>();
        cursor = super.query(USER_TABLE, USER_COLUMNS, null,
                null, COLUMN_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                User user = cursorToEntity(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return userList;
    }

    public boolean addUser(User user) {
        // set values
        setContentValue(user);
        try {
            return super.insert(USER_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean addUsers(List<User> users) {
        try{
            mDb.beginTransaction();

            for (User user : users) {
                addUser(user);
            }

            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
        return true;
    }

    protected User cursorToEntity(Cursor cursor) {

        User user = new User();

        int idIndex;
        int emailIndex;
        int passwordIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                user.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_EMAIL) != -1) {
                emailIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_EMAIL);
                user.email = cursor.getString(emailIndex);
            }
            if (cursor.getColumnIndex(COLUMN_PASSWORD) != -1) {
                passwordIndex = cursor.getColumnIndexOrThrow(COLUMN_PASSWORD);
                user.password = cursor.getString(passwordIndex);
            }

        }
        return user;
    }

    private void setContentValue(User user) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_EMAIL, user.email);
        initialValues.put(COLUMN_PASSWORD, user.password);
        initialValues.put(COLUMN_ID, user.id);
    }

    private ContentValues getContentValue() {
        return initialValues;
    }

    public boolean deleteAllUsers() {
        return super.delete(USER_TABLE,null,null)>0;
    }

    @Override
    public User fetchUserByEmail(String email) {
        final String selectionArgs[] = { email.toLowerCase() };
        final String selection = COLUMN_EMAIL + " = ?";
        User user = new User();
        cursor = super.query(USER_TABLE, USER_COLUMNS, selection,
                selectionArgs, COLUMN_ID);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                user = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }

        return user;
    }

}
