package com.example.transactionmanagerX.data;

import com.example.transactionmanagerX.data.primitives.Account;
import com.example.transactionmanagerX.data.primitives.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EverymanBank implements IExternalDataSource {
    private static Random random = new Random();
    String NAME = "EverymanBank";
    Account account;
    private static TransactionGenerator transactionGenerator = new TransactionGenerator();


    private Account jsonToAccount(JSONObject jsonInput, String institution, String login  ){
        Account result=new Account();
        result.institution=institution;
        result.login  =login;
        try {
            result.number=jsonInput.getString("number");
            result.last_sync=jsonInput.getLong("last_sync");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Account> getAccounts(String login )  {
        JSONObject jsonInput =transactionGenerator.getAccounts(login  ,NAME,2);
        List<Account> result = new ArrayList<>();
        if (jsonInput.has("accounts"))
        {
            String institution = null;
            try {
                institution = jsonInput.getString("institution");

            String cred = jsonInput.getString("login  ");
            JSONArray arr = jsonInput.getJSONArray("accounts");
            for (int i=0;i< arr.length(); ++i) {
                Account temp = new Account();
                result.add(jsonToAccount(arr.getJSONObject(i),institution ,cred));
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Transaction jsonToTransaction(JSONObject jsonObject){
       Transaction result= new Transaction();
        try {
            result.amount=jsonObject.getDouble("amount");
            result.payee=jsonObject.getString("payee");
            result.datetime=jsonObject.getLong("date");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return result;
    }
    @Override
    public List<Transaction> getAccountTransactions(Account account) {
        int count=random.nextInt(10);
        if (account.last_sync==0){
            count=30;
        }
        List<Transaction> result =new ArrayList<>();
        JSONObject jsonInput = transactionGenerator.getTransactions(account.login ,account.last_sync,account.number,count);
        if (jsonInput.has("transactions")){
            JSONArray arr = null;
            try {
                arr = jsonInput.getJSONArray("transactions");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0;i<arr.length();++i){
                try {
                    result.add(jsonToTransaction(arr.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public IExternalDataSource newInstance() {
        account=new Account();
        return this;
    }
}
