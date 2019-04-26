package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.User;

public interface IDataFacade {
    User getOrAddUser(String email, String password);

}
