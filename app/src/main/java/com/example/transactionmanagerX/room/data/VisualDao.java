package com.example.transactionmanagerX.room.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VisualDao {
    @Query("SELECT * FROM VisualData WHERE user_id = :user_id")
    public LiveData<List<VisualData>> getVisuals(Long user_id);

    @Insert
    List<Long> addVisual(VisualData... visuals);

    @Delete
    public void deleteVisual(VisualData visual);

    @Update
    public void updateVisual(VisualData... visuals);
}
