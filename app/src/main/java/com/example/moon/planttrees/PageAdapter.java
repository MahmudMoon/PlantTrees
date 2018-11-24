package com.example.moon.planttrees;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter{
    int tab_count ;

    public PageAdapter(FragmentManager fm, int tab_count) {
        super(fm);
        this.tab_count = tab_count;
    }


    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                ins1 i1 = new ins1();
                return i1;
            case 1:
                ins2 i2 = new ins2();
                return i2;

            case 2:
                ins3 i3 = new ins3();
                return i3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tab_count;
    }
}
