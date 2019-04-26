package com.example.transactionmanagerX;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.transactionmanagerX.TransactionFragment.OnListFragmentInteractionListener;
import com.example.transactionmanagerX.room.data.Transaction;


import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Transaction} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTransactionRecyclerViewAdapter extends RecyclerView.Adapter<MyTransactionRecyclerViewAdapter.ViewHolder> {

    private List<Transaction> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTransactionRecyclerViewAdapter( OnListFragmentInteractionListener listener) {
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues != null) {
            holder.mItem = mValues.get(position);
            holder.mDateView.setText(mValues.get(position).getDate().toString());
            holder.mPayeeView.setText(mValues.get(position).getPayee());
            holder.mAmountView.setText(String.valueOf(mValues.get(position).getAmount()));

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(holder.mItem);
                    }
                }

            });
        } else {
            holder.mDateView.setText("Nothing");
            holder.mPayeeView.setText("Nothing");
            holder.mAmountView.setText("$0.01");
        }
    }

    @Override
    public int getItemCount(){
        if (mValues !=null)
        return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mPayeeView;
        public final TextView mAmountView;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.item_date);
            mPayeeView = (TextView) view.findViewById(R.id.item_payee);
            mAmountView = (TextView) view.findViewById(R.id.item_amount);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mPayeeView.getText() + "'";
        }
    }

    void setTransactions(List<Transaction> transactions){
        mValues = transactions;
        notifyDataSetChanged();
    }

}
