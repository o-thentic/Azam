package com.tanzakesho.azam;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tanzakesho.framework.AzamBaseNav;


import java.util.Arrays;
import java.util.List;

public class Channels extends AzamBaseNav implements FragmentSubscriptionConfirmation.OnChoiceMadeListener {

    int mWidth;
    int mHeight;

    String mPackageValue;
    String costValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView channelDescription = (TextView) findViewById(R.id.channel_description);
        TextView channelCost = (TextView) findViewById(R.id.channel_cost);

        Bundle extras = getIntent().getBundleExtra("extra");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_channels);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mPackageValue = extras.getString("tOne");
        setContentTitle(mPackageValue);
        costValue = extras.getString("cost");
        channelCost.setText(costValue);

        channelDescription.setText(extras.getString("tTwo"));
        List<String> mChannels = Arrays.asList(extras.getStringArray("channels"));
        Log.i("mActive", mChannels.toString());
        recyclerView.setAdapter(new AzamChannelsRecyclerViewAdapter(mChannels));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;

        Button subscribe = (Button) findViewById(R.id.button_subscribe);

        ViewGroup.LayoutParams params = subscribe.getLayoutParams();
        params.width = mWidth/3;
        subscribe.setLayoutParams(params);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf");
        subscribe.setTypeface(typeface);
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
        super.initVariables(R.layout.activity_channels, R.id.toolbar_channel, R.id.channel_title, R.id.drawer_layout_channel, "Roboto-Light.ttf");
    }

    public void subConf(View view){
        FragmentManager fm = getSupportFragmentManager();
        FragmentSubscriptionConfirmation subConf = FragmentSubscriptionConfirmation.newInstance(mWidth, mHeight, mPackageValue, costValue);
        subConf.show(fm,null);

    }

    @Override
    public void onChoiceMade(Boolean choice) {
        if(choice){
            Packages.endActivity();
            finish();
        }
    }

}
