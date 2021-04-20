package com.example.adminportalptpn;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int number_tabs;

    public TabAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }


    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return ListOnLineFragment.newInstance("","");
            case 1:
                return ListOfflineFragment.newInstance("","");
            default:
                return ListOnLineFragment.newInstance("","");
        }
    }

    //Mengembalikan jumlah tampilan yang tersedia.
    @Override
    public int getCount() {
        return number_tabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
