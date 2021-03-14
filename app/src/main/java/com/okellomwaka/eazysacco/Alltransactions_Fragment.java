package com.okellomwaka.eazysacco;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.okellomwaka.eazysacco.Models.deposit;
import com.okellomwaka.eazysacco.Models.transaction;

import java.util.ArrayList;
import java.util.List;


public class Alltransactions_Fragment extends Fragment {
    String account;

    private int mColumnCount = 1;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
   AlltransactionsAdaptor adapter;
    List<transaction> transactions;


    public Alltransactions_Fragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Alltransactions_Fragment newInstance(String acc) {
        Alltransactions_Fragment fragment = new Alltransactions_Fragment();
        Bundle args = new Bundle();
        args.putString("account", acc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account = getArguments().getString("account");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_alltransactions_, container, false);
        // query all transactions
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        // Set the adapter
        transactions =  new ArrayList<transaction>();
        if (v instanceof RecyclerView) {
            Context context = v.getContext();
            RecyclerView recyclerView = (RecyclerView) v;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new AlltransactionsAdaptor(transactions,context);
            recyclerView.setAdapter(adapter);
        }

        Query query = firestore.collection("Transactions");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (!queryDocumentSnapshots.isEmpty()) {

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String ID = doc.getDocument().getId();

                            final transaction d= doc.getDocument().toObject(transaction.class).withID(ID);
                            transactions.add(d);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    Toast.makeText(getContext(), "no transactions", Toast.LENGTH_LONG).show();
                }

            }
        }) ;
    return v;
    }
}