package com.example.transactionmanagerX.data;

import java.util.HashMap;
import java.util.Map;

public class ExternalDataSourceFactory {
    private Map<String, IExternalDataSource> dataSources = new HashMap<>();
    public void registerDataSource(IExternalDataSource ds, String name){
        if (!dataSources.containsKey(name)){
            dataSources.put(name,ds);
        }
    }
    public IExternalDataSource getDataSource(String institution){
        return dataSources.get(institution);
    }
}
