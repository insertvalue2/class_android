package com.example.myviewpager2ex;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


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
public class MainActivity extends FragmentActivity {

    private static final int NUM_PAGES = 5;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate a ViewPager2 and a PagerAdapter.
        viewPager2 = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return new ScreenSlidePageFragment();
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }

}







