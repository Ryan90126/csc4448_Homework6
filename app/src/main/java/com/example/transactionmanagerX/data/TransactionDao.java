package com.example.transactionmanagerX.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.transactionmanagerX.data.primitives.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionDao extends DbContentProvider implements ITransactionDao, ITransactionSchema {

    private Cursor cursor;
    private ContentValues initialValues;
    private DateMath dateMath;

    public TransactionDao(SQLiteDatabase db){super(db);}

    private void setContentValue(Transaction transaction) {
        initialValues = new ContentValues();
        dateMath=new DateMath();
        initialValues.put(COLUMN_ID, transaction.id);
        initialValues.put(COLUMN_DATE, transaction.datetime.toString());
        initialValues.put(COLUMN_PAYEE, transaction.payee);
        initialValues.put(COLUMN_AMOUNT, transaction.id);
        initialValues.put(COLUMN_USER_ID, transaction.user_id);
        initialValues.put(COLUMN_ACCOUNT_ID, transaction.account_id);
        initialValues.put(COLUMN_LABEL_ID, transaction.label_id);
    }

    private ContentValues getContentValue(){return initialValues;}

    @Override
    protected Transaction cursorToEntity(Cursor cursor) {

        Transaction transaction = new Transaction();

        int idIndex;
        int dateIndex;
        int payeeIndex;
        int labelIndex;
        int amountIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                transaction.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_PAYEE) != -1) {
                payeeIndex = cursor.getColumnIndexOrThrow(COLUMN_PAYEE);
                transaction.payee = cursor.getString(payeeIndex);
            }
            if (cursor.getColumnIndex(COLUMN_AMOUNT) != -1) {
                amountIndex = cursor.getColumnIndexOrThrow(COLUMN_AMOUNT);
                transaction.amount = cursor.getDouble(amountIndex);
            }
            if (cursor.getColumnIndex(COLUMN_LABEL_ID) != -1) {
                labelIndex = cursor.getColumnIndexOrThrow(COLUMN_LABEL_ID);
                transaction.label_id = cursor.getInt(labelIndex);
            }
            if (cursor.getColumnIndex(COLUMN_DATE) != -1) {
                dateIndex = cursor.getColumnIndexOrThrow(COLUMN_DATE);
                transaction.datetime = cursor.getLong(dateIndex);
            }

        }
        return transaction;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        setContentValue(transaction);
        try {
            return super.insert(TRANSACTION_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteTransaction(int id) {
        final String selectionArgs[] = { String.valueOf(id) };
        final String selection = COLUMN_ID + " = ?";
        try {
            return super.delete(TRANSACTION_TABLE,selection,selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Transaction> getTransactionsBatch(int user_id, Long start, int count) {
        List<Transaction> transactions = new ArrayList<>();
        final String selectionArgs[] = {String.valueOf(user_id), start.toString()};
        final String selection = COLUMN_USER_ID +" = ? AND " + COLUMN_DATE + " < ? ";
        cursor = super.query(TRANSACTION_TABLE,TRANSACTION_COLUMNS,selection,selectionArgs,COLUMN_DATE + " DESC ",String.valueOf(count));
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transactions.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByLabel(int user_id, Long start, int label_id, int count) {
        List<Transaction> transactions = new ArrayList<>();
        final String selectionArgs[] = {String.valueOf(user_id), start.toString(),String.valueOf(label_id)};
        final String selection = COLUMN_USER_ID +" = ? AND " + COLUMN_DATE + " < ? AND "+COLUMN_LABEL_ID + " = ?";
        cursor = super.query(TRANSACTION_TABLE,TRANSACTION_COLUMNS,selection,selectionArgs,COLUMN_DATE + " DESC ",String.valueOf(count));
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transactions.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return transactions;
    }

    @Override
    public Double getTransactionSum(int user_id, Long end) {
        Double sum = new Double(0);
        Transaction transaction = new Transaction();
        final String selectionArgs[] = {String.valueOf(user_id), end.toString()};
        final String selection = COLUMN_USER_ID +" = ? AND " + COLUMN_DATE + " > ? ";
        cursor = super.query(TRANSACTION_TABLE,TRANSACTION_COLUMNS,selection,selectionArgs,COLUMN_DATE + " DESC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transaction = cursorToEntity(cursor);
                sum+=transaction.amount;
                cursor.moveToNext();
            }
        }
        return sum;
    }

    @Override
    public Double getLabelSum(int user_id, Long end, int label_id) {
        Double sum = new Double(0);
        Transaction transaction = new Transaction();
        final String selectionArgs[] = {String.valueOf(user_id), end.toString(),String.valueOf(label_id)};
        final String selection = COLUMN_USER_ID +" = ? AND " + COLUMN_DATE + " > ? AND "+COLUMN_LABEL_ID + " = ?";
        cursor = super.query(TRANSACTION_TABLE,TRANSACTION_COLUMNS,selection,selectionArgs,COLUMN_DATE + " DESC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transaction = cursorToEntity(cursor);
                sum+=transaction.amount;
                cursor.moveToNext();
            }
        }
        return sum;
    }

    @Override
    public int setTransactionsLabel(List<Integer> transaction_ids, int label_id) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_LABEL_ID,label_id);
        String dbList = "( ";
        Iterator<Integer> id = transaction_ids.iterator();
        while(id.hasNext()){
            dbList += String.valueOf(id);
            if(id.hasNext()){
                dbList+=", ";
            }
            id.next();
        }
        dbList+=" )";
        final String selectionArgs[]={dbList};
        final String selection = COLUMN_ID +" IN ?";
        return super.update(TRANSACTION_TABLE,initialValues,selection,selectionArgs);
    }

    @Override
    public List<Transaction> loadTransactions(int user_id, Long end) {
        List<Transaction> transactions = new ArrayList();
        final String selectionArgs[] = {String.valueOf(user_id), end.toString()};
        final String selection = COLUMN_USER_ID +" = ? AND " + COLUMN_DATE + " > ? ";
        cursor = super.query(TRANSACTION_TABLE,TRANSACTION_COLUMNS,selection,selectionArgs,COLUMN_DATE + " DESC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                transactions.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return transactions;
    }
}
