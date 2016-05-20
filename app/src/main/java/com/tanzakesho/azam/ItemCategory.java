package com.tanzakesho.azam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * Created by othmankulindwa on 3/11/16.
 */
public class ItemCategory {

    public String heading;
    public String category;
    public Bitmap bitmap;
    public String id;

    public ItemCategory(String heading, String base64, String id, String category){
        this.heading = heading;
        this.category = category;
        this.id = id;

        String pureBase64 = base64.substring(base64.indexOf(",")+1);

        byte[] bytes = Base64.decode(pureBase64, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}