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

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class deposits_fragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    String acc;

    private int mColumnCount = 1;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    DepositRecyclerViewAdapter adapter;
    List<deposit> mydeposist;


    public deposits_fragment() {
    }

    public static deposits_fragment newInstance(int columnCount, String acc) {
        deposits_fragment fragment = new deposits_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString("acc",acc);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            acc = getArguments().getString("acc");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deposits_fragment_list, container, false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new DepositRecyclerViewAdapter(mydeposist,context));
        }


        Query query = firestore.collection("SaccoAccounts").document(acc)
                .collection("deposits").orderBy("deposit_timestamp", Query.Direction.DESCENDING);
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                if (!queryDocumentSnapshots.isEmpty()) {

                    for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            String postID = doc.getDocument().getId();

                            final deposit d= doc.getDocument().toObject(deposit.class).withID(postID);
                                mydeposist.add(d);
                            adapter.notifyDataSetChanged();
                        }
                    }

                }else {
                    Toast.makeText(getContext(), "no transactions", Toast.LENGTH_LONG).show();
                }

            }
        }) ;
        return view;
    }
}