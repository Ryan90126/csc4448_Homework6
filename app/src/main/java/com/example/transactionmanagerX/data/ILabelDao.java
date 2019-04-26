package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.Label;

import java.util.List;

public interface ILabelDao {
    public boolean addLabel(Label label);
    public List<Label> getLabels (int user_id);
}
