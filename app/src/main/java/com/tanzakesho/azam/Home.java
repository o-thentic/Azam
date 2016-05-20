package com.tanzakesho.azam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.tanzakesho.azam.tabs.SlidingTabLayout;
import com.tanzakesho.framework.AzamBaseNav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Home extends AzamBaseNav implements FragmentItem.OnListFragmentInteractionListener {

    public final static String AZAMTV_CAT = "Azam TV";
    public final static String AZAMMARINE_CAT = "Azam Marine";
    public final static String FOODSANDDRINKS_CAT = "Food&Drinks";

    private ViewPager mPager;
    private SlidingTabLayout mTabs;
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitles = new ArrayList<>(Arrays.asList("Azam TV", "Azam Marine", "Food&Drinks"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragments.add(FragmentItem.newInstance(1, AZAMTV_CAT, this));
        mFragments.add(FragmentItem.newInstance(1, AZAMMARINE_CAT, this));
        mFragments.add(FragmentItem.newInstance(1, FOODSANDDRINKS_CAT, this));

        mPager = (ViewPager) findViewById(R.id.pager);
        mTabs = (SlidingTabLayout) findViewById(R.id.tabs);
        mTabs.setDistributeEvenly(true);
        mPager.setAdapter(new HomePagerAdapter(getSupportFragmentManager()));
        mTabs.setViewPager(mPager);
        mTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {

            /**
             * @param position
             * @return return the color of the indicator used when {@code position} is selected.
             */
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.White);
            }


        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initVariables(int mContentViewId, int mToolBarId, int mTitleTextId, int mDrawerId, String mTypeFace) {
        super.initVariables(R.layout.activity_home, R.id.toolbar, R.id.home_title, R.id.drawer_layout, "Roboto-Light.ttf");
    }

    @Override
    public void onListFragmentInteraction(ItemCategory item) {
        if(item.heading.equals("AZAM PACKAGES")){
            Intent intent = new Intent(Home.this, Packages.class);
            startActivity(intent);

        }else if(item.heading.equals("AZAM NEWS")){

        }else if(item.heading.equals("FOOTBALL")){

        }else if(item.heading.equals("BOOKING")){
            Intent intent = new Intent(Home.this, Booking.class);
            startActivity(intent);

        }else if(item.heading.equals("NEWS ALERT")){

        }else if(item.heading.equals("SPECIAL OFFERS")){

        }else if(item.heading.equals("GRAINS")){

        }else if(item.heading.equals("DRINKS")){

        }else if(item.heading.equals("FROZEN")){

        }
    }

    class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return mFragments.size();
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            super.getPageTitle(position);
            return mTitles.get(position);
        }
    }

}
