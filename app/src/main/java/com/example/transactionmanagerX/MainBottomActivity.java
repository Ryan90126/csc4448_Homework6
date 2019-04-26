package com.example.transactionmanagerX;

import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.example.transactionmanagerX.room.data.Transaction;
import com.example.transactionmanagerX.ui.main.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

public class MainBottomActivity extends AppCompatActivity implements TransactionFragment.OnListFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener {

    private MainViewModel mainViewModel;

    private TextView mTextMessage;
    private List<Fragment> fragmentList;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    switchToFragment(1);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_transaction:
                    switchToFragment(0);
                    return true;
            }
            return false;
        }
    };


    public void switchToFragment(int i) {
        Fragment newFragment=fragmentList.get(i);
        FragmentTransaction ftransaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        ftransaction.replace(R.id.fragment_container, newFragment);
        //ftransaction.addToBackStack(null);

        // Commit the transaction
        ftransaction.commit();
    }

    private void fillFragmentList(){
        fragmentList=new ArrayList<>();
        fragmentList.add(new TransactionFragment());
        fragmentList.add(new BlankFragment());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom);
        fillFragmentList();
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            fragmentList.get(0).setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragmentList.get(1)).commit();
        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Transaction item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
