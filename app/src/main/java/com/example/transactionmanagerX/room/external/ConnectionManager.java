package com.example.transactionmanagerX.room.external;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    Map<String,BankConnection> bankConnections;
    ConnectionManager (){
        bankConnections = new HashMap<>();
        registerBankConnection(new EverymanBankConnector());
    }

    private void registerBankConnection(BankConnection bank){
        bankConnections.put(bank.NAME,bank);
    }

    public BankConnection getConnection(String bankName){
        return bankConnections.get(bankName).newInstance();
    }


}
