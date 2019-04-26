package com.example.transactionmanagerX.room.data;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.transactionmanagerX.room.external.BankConnection;
import com.example.transactionmanagerX.room.external.ConnectionManager;
import com.example.transactionmanagerX.room.external.DateMath;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataRepository {
    private static DateMath dateMath = new DateMath();
    private AccountDao accountDao;
    private LabelDao labelDao;
    private TransactionDao transactionDao;
    private UserDao userDao;
    private VisualDao visualDao;
    private MutableLiveData<Long> currentUserID;
    private LiveData<List<Transaction>> mTransactions;

    private List<Account> accounts;


    BankConnection bankConnection;
    ConnectionManager connectionManager;

    public DataRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        accountDao = db.accountDao();
        labelDao= db.labelDao();
        transactionDao=db.transactionDao();
        userDao=db.userDao();
        visualDao=db.visualDao();
    }
    public void syncUser(User user) {
       LiveData<List<Transaction>> mTransactions = transactionDao.getTransactions(user.uid);
    }

    public LiveData<List<Transaction>> getUserTransactions(Long user_id){
        return transactionDao.getTransactions(user_id);
    }

    public LiveData<List<VisualData>> getUserVisuals(Long user_id){
        return visualDao.getVisuals(user_id);
    }

    public MutableLiveData<Long> getCurrentUserID(){
        return currentUserID;
    }






    public User getOrAddUser(String email, String password){
        User temp = userDao.fetchUserByEmail(email.toLowerCase());
        if(temp==null){
            temp = new User();
            temp.email=email.toLowerCase();
            temp.password=password;
            temp.uid=userDao.insertAll(temp).get(0);
        }
        return temp;
    }

    public void insertTransactionList(List<Transaction> transactions){
        new insertTransactionListAsyncTask(transactionDao).execute(transactions);
    }

    public void insertTransaction(Transaction transaction){
        List<Transaction> temp =java.util.Arrays.asList(transaction);
        new insertTransactionListAsyncTask(transactionDao).execute(temp);
    }

    private static class insertTransactionListAsyncTask extends AsyncTask<List<Transaction>, Void, Void> {

        private TransactionDao mAsyncTaskDao;

        insertTransactionListAsyncTask(TransactionDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<Transaction>... params) {
            mAsyncTaskDao.addTransactionList(params[0]);
            return null;
        }
    }

        List<Account> getAccounts(Long userID){
        return  accountDao.fetchAccounts(userID);
    }

    void syncUser(Long userID) {
        this.accounts=getAccounts(userID);
        if(accounts.size()<1){
            bankConnection = connectionManager.getConnection("EverymanBank");
            bankConnection.connect("","");
            accounts=bankConnection.getAccountInfo();
        }
        accountDao.addAccounts(accounts);
        accounts
    }

    public void syncAccounts(Account... accounts){
        for(Account account : accounts){

            bankConnection= connectionManager.getConnection(account.institution);
            bankConnection.connect(account.login,account.password);
            List<Transaction> transactionList=bankConnection.syncTransactions(account);
            for(Transaction temp : transactionList){
                temp.account_id=account.id;
                temp.user_id=account.user_id;
            }
        insertTransactionList(transactionList);
        }
    }
}
