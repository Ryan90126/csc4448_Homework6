package com.example.transactionmanagerX.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.transactionmanagerX.data.primitives.VisualData;

import java.util.ArrayList;
import java.util.List;

public class VisualDao extends DbContentProvider implements IVisualDao, IVisualScheme {

    private Cursor cursor;
    private ContentValues initialValues;

    public VisualDao(SQLiteDatabase db){super (db);}

    @Override
    protected VisualData cursorToEntity(Cursor cursor) {
        VisualData vd = new VisualData();

        int idIndex;
        int nameIndex;
        int labelsIndex;
        int rangeIndex;
        int typeIndex;

        if (cursor != null) {
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
                vd.id = cursor.getInt(idIndex);
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                nameIndex = cursor.getColumnIndexOrThrow(
                        COLUMN_NAME);
                vd.name = cursor.getString(nameIndex);
            }
            if (cursor.getColumnIndex(COLUMN_LABEL_IDS) != -1) {
                labelsIndex = cursor.getColumnIndexOrThrow(COLUMN_LABEL_IDS);
                vd.label_ids = cursor.getString(labelsIndex);
            }
            if (cursor.getColumnIndex(COLUMN_RANGE) != -1) {
                rangeIndex = cursor.getColumnIndexOrThrow(COLUMN_RANGE);
                vd.range = cursor.getInt(rangeIndex);
            }
            if (cursor.getColumnIndex(COLUMN_TYPE) != -1) {
                typeIndex = cursor.getColumnIndexOrThrow(COLUMN_TYPE);
                vd.graphType = cursor.getInt(typeIndex);
            }


        }
        return vd;
    }

    @Override
    public List<VisualData> getVisuals(int user_id) {
        List<VisualData> vds = new ArrayList<>();
        final String selectionArgs[] = {String.valueOf(user_id)};
        final String selection = COLUMN_USER_ID +" = ? ";
        cursor = super.query(VISUAL_TABLE,VISUAL_COLUMNS,selection,selectionArgs, COLUMN_ID + " ASC ");
        if(cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                vds.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return vds;
    }

    private void setContentValue(VisualData vd) {
        initialValues = new ContentValues();
        initialValues.put(COLUMN_ID, vd.id);
        initialValues.put(COLUMN_NAME, vd.name);
        initialValues.put(COLUMN_LABEL_IDS, vd.label_ids);
        initialValues.put(COLUMN_TYPE, vd.graphType);
        initialValues.put(COLUMN_RANGE, vd.range);
        initialValues.put(COLUMN_USER_ID, vd.user_id);
    }

    private ContentValues getContentValue() {
        return initialValues;
    }


    @Override
    public boolean addVisual(VisualData visual) {
        initialValues = new ContentValues();
        setContentValue(visual);
        try {
            return super.insert(VISUAL_TABLE, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteVisual(int visual_id) {
        final String selectionArgs[] = { String.valueOf(visual_id) };
        final String selection = COLUMN_ID + " = ?";
        try {
            return super.delete(VISUAL_TABLE,selection,selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            Log.w("Database", ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateVisual(VisualData vd) {
        initialValues = new ContentValues();
        setContentValue(vd);
        final String selectionArgs[] = {String.valueOf(vd.id)};
        final String selection = COLUMN_ID +" IN ?";
        return 0<super.update(VISUAL_TABLE,getContentValue(),selection,selectionArgs);
    }

}
