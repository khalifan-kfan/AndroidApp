package com.okellomwaka.eazysacco;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.okellomwaka.eazysacco.Models.deposit;
import com.okellomwaka.eazysacco.Models.transaction;

import java.util.List;

public class AlltransactionsAdaptor extends   RecyclerView.Adapter<AlltransactionsAdaptor.ViewHolder> {
    final List<transaction> mValues;
    FirebaseAuth auth;

    public AlltransactionsAdaptor(List<transaction> transactionList, Context context) {
        mValues=transactionList;

    }

    @NonNull
    @Override
    public AlltransactionsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);
        auth = FirebaseAuth.getInstance();
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlltransactionsAdaptor.ViewHolder holder, int position) {
        //due to initially posted bad data
       if(mValues.get(position).getMobile()!=null
               ||mValues.get(position).getAccount()!=null||
               mValues.get(position).getAmount()!=null||mValues.get(position).getUserId()!=null){

           holder.number.setText(mValues.get(position).getMobile());
           holder.accont.setText(mValues.get(position).getAccount());
           holder.amount.setText(mValues.get(position).getAmount());
       }


    }

    @Override
    public int getItemCount() {
        if(mValues!=null){
            return mValues.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView number,amount;
        public final TextView accont;
        public final LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            number = (TextView) itemView.findViewById(R.id.mobile);
            amount = (TextView) itemView.findViewById(R.id.amount);
            accont = (TextView) itemView.findViewById(R.id.account);
            linearLayout = itemView.findViewById(R.id.layer);

        }
    }
}
