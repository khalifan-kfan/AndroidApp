package com.okellomwaka.eazysacco;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Withdraw_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Withdraw_Fragment extends Fragment {


    public Withdraw_Fragment() {
        // Required empty public constructor
    }


    public static Withdraw_Fragment newInstance() {
        Withdraw_Fragment fragment = new Withdraw_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdraw_, container, false);
    }
}