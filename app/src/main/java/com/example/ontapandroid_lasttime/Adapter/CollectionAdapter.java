package com.example.ontapandroid_lasttime.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.ontapandroid_lasttime.Fragment.Fragment_DongVat;
import com.example.ontapandroid_lasttime.Fragment.Fragment_TT;

public class CollectionAdapter extends FragmentStateAdapter {

    int tabCount = 2;

    Fragment_DongVat fragmentDongVat;
    Fragment_TT fragmentTt;
    public CollectionAdapter(@NonNull Fragment fragment) {
        super(fragment);
        fragmentDongVat = new Fragment_DongVat();
        fragmentTt = new Fragment_TT();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return fragmentDongVat;
            case 1:
                return fragmentTt;
            default:
                return fragmentDongVat;
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
