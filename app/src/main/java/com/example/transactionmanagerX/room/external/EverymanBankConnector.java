package com.example.transactionmanagerX.room.external;

import com.example.transactionmanagerX.room.data.Account;
import com.example.transactionmanagerX.room.data.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class EverymanBankConnector extends BankConnection {
    String login = new String();
    String password = new String();

    private static Random random = new Random();
    public static String NAME = "EverymanBank";
    Account account;
    private static TransactionGenerator transactionGenerator = new TransactionGenerator();


    private Account jsonToAccount(JSONObject jsonInput, String institution, String login, String password  ){
        Account result=new Account();
        result.institution=institution;
        result.login  =login;
        result.password = password;
        try {
            result.number=jsonInput.getString("number");
            result.last_sync=jsonInput.getLong("last_sync");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Account> getAccountInfo(){
        return myGetAccountInfo(this.login, this.password);
    }

    public List<Account> myGetAccountInfo(String login, String password)  {
        JSONObject jsonInput =transactionGenerator.getAccounts(login,password,NAME,1);
        List<Account> result = new ArrayList<>();
        if (jsonInput.has("accounts"))
        {
            String institution = null;
            try {
                institution = jsonInput.getString("institution");

                JSONArray arr = jsonInput.getJSONArray("accounts");
                for (int i=0;i< arr.length(); ++i) {
                    Account temp = new Account();
                    result.add(jsonToAccount(arr.getJSONObject(i),institution ,login, password));
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
            result.setAmount(jsonObject.getDouble("amount"));
            result.setPayee(jsonObject.getString("payee"));
            result.setDate(jsonObject.getLong("date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Transaction> syncTransactions(Account account) {
        int count = random.nextInt(10);
        if (account.last_sync == 0) {
            count = 30;
        }
        List<Transaction> result = new ArrayList<>();
        JSONObject jsonInput = transactionGenerator.getTransactions(account.login, account.last_sync, account.number, count);
        if (jsonInput.has("transactions")) {
            JSONArray arr = null;
            try {
                arr = jsonInput.getJSONArray("transactions");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < arr.length(); ++i) {
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
    public void close() {

    }

    BankConnection newInstance(){
        return new EverymanBankConnector();
    }

    @Override
    public int connect(String login, String password) {
        this.login=login;
        this.password=password;
        return 1;
    }


}
