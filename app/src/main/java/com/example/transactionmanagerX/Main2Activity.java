package com.example.transactionmanagerX;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.transactionmanagerX.data.primitives.Transaction;
import com.example.transactionmanagerX.data.TransactionTabDataFacade;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    private TransactionTabDataFacade mDb;
    private List<Transaction> transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        setContentView(linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        mDb=new TransactionTabDataFacade(this);
        mDb.open();
        mDb.setUserID(getIntent().getIntExtra("user_id",-1));
        mDb.generateATransaction();
        transactions=mDb.loadTransactions();
        for(Transaction t : transactions) {
            TextView textView = new TextView(this);
            textView.setText(t.payee);
            linearLayout.addView(textView);
            textView = new TextView(this);
            textView.setText(t.amount.toString());
            linearLayout.addView(textView);
        }

    }

}
