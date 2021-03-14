package com.okellomwaka.eazysacco;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private int Numberoftabs;
    public Context mContext;
    String acc;


    public SectionsPagerAdapter(@NonNull FragmentManager fm,Context context,String acc,int Numberoftabs) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.Numberoftabs = Numberoftabs;
        mContext = context;
        this.acc = acc;


    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Alltransactions_Fragment tab1 = Alltransactions_Fragment.newInstance(acc);
                return  tab1;
            case 1:
               deposits_fragment tab2 =  deposits_fragment.newInstance(1,acc);
                return tab2;
            case 2:
               Withdraw_Fragment  tab3 = Withdraw_Fragment.newInstance();
                return tab3;
            default:
                Toast.makeText(mContext,"Something went wrong",Toast.LENGTH_LONG).show();
                return null;
        }
    }
    @Override
    public int getCount() {
        return Numberoftabs;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "All transactions";
            case 1:
                return "Deposits";
            case 2:
                return "Withdraws";
            default:
                return null;
        }
    }
}
