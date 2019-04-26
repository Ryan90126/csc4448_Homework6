package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.VisualData;

import java.util.List;

public interface IVisualDao {
    public List<VisualData> getVisuals(int user_id);
    public boolean addVisual (VisualData visual);
    public boolean deleteVisual(int visual_id);
    public boolean updateVisual(VisualData vd);
}
