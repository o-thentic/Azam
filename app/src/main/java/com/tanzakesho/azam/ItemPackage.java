package com.tanzakesho.azam;

import android.util.Log;
import java.util.List;

/**
 * Created by othmankulindwa on 3/11/16.
 */
public class ItemPackage {

    public String mTitleOne;
    public String mTitleTwo;
    public String mCategory;
    public String mCost;
    public String id;
    public final List<String> mChannels;

    public ItemPackage(String titleOne, String titleTwo, String id, List<String> channels, String category, String cost){
        this.mTitleOne = titleOne;
        this.mTitleTwo = titleTwo;
        this.mChannels = channels;
        this.mCategory = category;
        this.mCost = cost;
        this.id = id;
        Log.i("packageInfoChannel", mChannels.toString());


    }
}