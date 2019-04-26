package com.example.transactionmanagerX.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.transactionmanagerX.data.primitives.Label;

import java.util.ArrayList;
import java.util.List;

public class LabelDao extends DbContentProvider implements ILabelDao, ILabelScheme {

    private Cursor cursor;
    private ContentValues initialValues;

    public LabelDao(SQLiteDatabase db) {
        super(db);
    }
    @Override
    protected Label cursorToEntity(Cursor cursor) {
        Label label = new Label();

        int idIndex;
        int nameIndex;
        int userIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                label.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                nameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME);
                label.name = cursor.getString(nameIndex);
            }
            if (cursor.getColumnIndex(COLUMN_USER_ID) != -1) {
                userIndex = cursor.getColumnIndexOrThrow(COLUMN_USER_ID);
                label.user_id = cursor.getInt(userIndex);
            }
        }
        return label;
    }

    @Override
    public boolean addLabel(Label label) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID,label.id);
        initialValues.put(COLUMN_NAME,label.name);
        initialValues.put(COLUMN_USER_ID, label.user_id);
        try {
            return super.insert(LABEL_TABLE, initialValues) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Label> getLabels(int user_id) {
        List<Label> labels = new ArrayList<>();
        final String selectionArgs[] = {String.valueOf(user_id)};
        final String selection = COLUMN_USER_ID +" = ? ";
        cursor = super.query(LABEL_TABLE,LABEL_COLUMNS,selection,selectionArgs, COLUMN_ID + " ASC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                labels.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return labels;
    }
}
