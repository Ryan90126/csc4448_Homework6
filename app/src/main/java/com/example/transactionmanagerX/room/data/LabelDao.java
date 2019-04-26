package com.example.transactionmanagerX.room.data;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LabelDao {
    @Insert
    Long addLabel(Label label);

    @Query("SELECT * FROM label WHERE user_id =:user_id ")
    public List<Label> getLabels(Long user_id);
}
