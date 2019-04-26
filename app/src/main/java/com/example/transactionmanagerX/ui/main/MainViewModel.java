package com.example.transactionmanagerX.ui.main;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;


import com.example.transactionmanagerX.data.Database;
import com.example.transactionmanagerX.room.data.DataRepository;
import com.example.transactionmanagerX.room.data.MyRoomDatabase;
import com.example.transactionmanagerX.room.data.Transaction;
import com.example.transactionmanagerX.room.data.User;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private DataRepository dataRepository;
    private LiveData<List<Transaction>> transactions;
    public MutableLiveData<Long> currentUserId;

    public MutableLiveData<Long> getCurrentUserId(){
        return dataRepository.getCurrentUserID();
    }


    public MainViewModel (Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        transactions = dataRepository.getUserTransactions(currentUserId.getValue());
    }
    LiveData<List<Transaction>> getTransactions(){return transactions;}
    public void insert(Transaction transaction){ dataRepository.insertTransaction(transaction);}

    public User getOrAddUser(String email, String password){
        return dataRepository.getOrAddUser(email, password);
    }
}
