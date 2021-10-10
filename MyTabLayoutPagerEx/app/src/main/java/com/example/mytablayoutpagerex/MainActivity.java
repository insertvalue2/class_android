package com.example.mytablayoutpagerex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;


/**
 * 프래그먼트 액티비티란 개념이 나왔는데, 이 액티비티는 간단히 말해서
 * 하나의 액티비티안에 여러 개의 액티비티를 보여주고 싶다는 목적으로 사용한다.
 * (실제로 액티비티를 여러개 넣는 것이 아니고 그렇게 보이도록 하는 것)
 */
/*
 * FragmentActivity 를 고려해야 하는 유일한 경우는 만약 당신이
 *  중첩된 fragment 를 사용하고 싶어할 때입니다.
 *  왜냐하면 중첩된 fragment 는 API 레벨 17까지
 *  네이티브 fragment 에서 지원하지 않았기 때문입니다.
 *
 * */
public class MainActivity extends AppCompatActivity {
    static final int TAB_NUMBER = 3;

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

        // viewPager 생성
        ViewPager viewPager = findViewById(R.id.view_pager);
        // viewPager 를 위한 어댑터 생성
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager(), TAB_NUMBER);
        // viewPager 에 어댑터 연결
        viewPager.setAdapter(adapter);


        // 연결 하기 viewPager <------> TabLayout

        //1. 탭 레이아웃 이벤트 리스너에 뷰 페이저 동작 연결
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //2. 뷰 페이저에 이벤트 리스너에 탭 레이아웃 동작 연결
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
        );


    }
}


class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public FragmentPagerAdapter(@NonNull FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 2) {
            return new FragmentThree();
        } else if (position == 1) {
            return new FragmentTwo();
        } else {
            return new FragmentOne();
        }
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