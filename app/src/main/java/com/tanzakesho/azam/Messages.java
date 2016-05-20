package com.tanzakesho.azam;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tanzakesho.framework.AzamBaseNav;

/**
 * Created by othmankulindwa on 5/5/16.
 */
public class Messages extends AzamBaseNav {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        super.initVariables(R.layout.activity_messages, R.id.toolbar_messages, R.id.messages_title, R.id.drawer_layout_messages, "Roboto-Light.ttf");
    }
}
