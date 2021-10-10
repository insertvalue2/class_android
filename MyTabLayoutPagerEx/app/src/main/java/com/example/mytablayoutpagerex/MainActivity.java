package com.example.mytablayoutpagerex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // 원하는만큼 탭을 더해준다.
        tabLayout.addTab(tabLayout.newTab().setText("One"));
        tabLayout.addTab(tabLayout.newTab().setText("Two"));
        tabLayout.addTab(tabLayout.newTab().setText("Three"));
        // TabLayout 과 pager 를 연동하기 위해서 리스너를 달아 줘야 한다.

    }
}


class PagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public PagerAdapter(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 :
                new FragmentOne();
                break;
            case 1 :
                new FragmentTwo();
                break;
            default:
                new FragmentThree();
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}



// Pager 를 사용하기 위해서 Adapter 를 만들어야 한다.
/*
--------------
|     |      |
|  1  |  2   |
|     |      |
--------------
*/