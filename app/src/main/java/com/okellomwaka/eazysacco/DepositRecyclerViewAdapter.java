package com.okellomwaka.eazysacco;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.okellomwaka.eazysacco.Models.deposit;

import java.util.List;


public class DepositRecyclerViewAdapter extends RecyclerView.Adapter<DepositRecyclerViewAdapter.ViewHolder> {

     final List<deposit> mValues;
     FirebaseAuth auth;

    public DepositRecyclerViewAdapter(List<deposit> items, Context context) {
        mValues=items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deposit_item, parent, false);
        auth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       // holder.mItem = mValues.get(position);
        if(mValues.get(position).getUser_id().equals(auth.getCurrentUser().getUid())){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#add8e6"));
        }
        holder.mIdView.setText(mValues.get(position).getAccount());
        holder.mContentView.setText(mValues.get(position).getDeposit());

    }

    @Override
    public int getItemCount() {
        if(mValues!=null){
            return mValues.size();
        }else {
            return 0;
        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final LinearLayout linearLayout;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.amount);
            mContentView = (TextView) view.findViewById(R.id.time);
            linearLayout = view.findViewById(R.id.layer);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}