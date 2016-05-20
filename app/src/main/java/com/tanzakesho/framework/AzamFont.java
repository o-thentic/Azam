package com.tanzakesho.framework;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.tanzakesho.azam.R;

import java.util.HashMap;

/**
 * Created by othmankulindwa on 3/5/16.
 */
public class AzamFont extends TextView {
    public AzamFont(Context context) {
        super(context);
        applyCustomFont(context, null);
    }

    public AzamFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context, attrs);
    }

    public AzamFont(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {

        TypedArray attributeArray = context.obtainStyledAttributes(attrs, R.styleable.AzamFont);

        String fontName = attributeArray.getString(R.styleable.AzamFont_font);

        Typeface typeface = selectTypeface(context, fontName);

        setTypeface(typeface);

        attributeArray.recycle();
    }

    private Typeface selectTypeface(Context context, String fontName){

        switch (fontName){
            case "RobotoRegular":
                return FontCache.getTypeface("Roboto-Regular.ttf", context);

            case "RobotoLight":
                return FontCache.getTypeface("Roboto-Light.ttf", context);

            case "RobotoThin":
                return FontCache.getTypeface("Roboto-Thin.ttf", context);

            default:
                return null;

        }
    }


}


