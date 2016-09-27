package com.lulu.player.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.lulu.player.R;
import com.lulu.player.adapter.FragmentsAdapter;
import com.lulu.player.base.BaseActivity;
import com.lulu.player.base.BaseFragment;
import com.lulu.player.main.diamond.view.DiamondFragment;
import com.lulu.player.main.free.view.FreeFragment;
import com.lulu.player.main.gold.view.GoldFragment;
import com.lulu.player.main.my.view.MyFragment;
import com.lulu.player.view.NoSlideViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


/**
 * @author zxc
 * @time 2016/9/26 0026下午 2:40
 */
public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    @Bind(R.id.content)
    NoSlideViewPager mViewPager;

    @Bind(R.id.title_text)
    TextView titleText;

    @Bind(R.id.tab_group)
    RadioGroup mTabGroup;

    private List<BaseFragment> mFragments;

    private FragmentsAdapter mFragmentsAdapter;

    private List<String> titles;

    private List<RadioButton> mTabItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewPager();
        initTabBar();
    }

    private void initViewPager() {
        mFragments = new ArrayList<BaseFragment>();
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                mTabGroup.check(mTabItems.get(position).getId());
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {

            }
        });
        mFragmentsAdapter = new FragmentsAdapter(fm, mFragments);
        mViewPager.setAdapter(mFragmentsAdapter);
        FreeFragment freeFragment = FreeFragment.newInstance(getResources().getString(R.string.main_free), 0);
        GoldFragment goldFragment = GoldFragment.newInstance(getResources().getString(R.string.main_gold), 1);
        DiamondFragment diamondFragment = DiamondFragment.newInstance(getResources().getString(R.string.main_diamond),
                2);
        MyFragment myFragment = MyFragment.newInstance(getResources().getString(R.string.main_my), 3);

        mFragments.add(freeFragment);
        mFragments.add(goldFragment);
        mFragments.add(diamondFragment);
        mFragments.add(myFragment);
        mFragmentsAdapter.notifyDataSetChanged();
    }

    public void initTabBar() {
        // TODO Auto-generated method stub
        mTabItems = new ArrayList<RadioButton>();
        mTabGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int currentIndex = 0;
                switch (checkedId) {
                    case R.id.tab_free:
                        currentIndex = 0;
                        break;
                    case R.id.tab_gold:
                        currentIndex = 1;
                        break;
                    case R.id.tab_diamond:
                        currentIndex = 2;
                        break;
                    case R.id.tab_my:
                        currentIndex = 3;
                        break;
                }
                mViewPager.setCurrentItem(currentIndex);
                titleText.setText(titles.get(currentIndex));
            }
        });

        for (int i = 0; i < mTabGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) mTabGroup.getChildAt(i);
            mTabItems.add(radioButton);
        }

    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle() {
        title = titles.get(0);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initAdapters() {

    }

    @Override
    protected void initDatas() {
        titles = new ArrayList<String>();
        titles.add("体验区");
        titles.add("黄金区");
        titles.add("钻石区");
        titles.add("我的");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
