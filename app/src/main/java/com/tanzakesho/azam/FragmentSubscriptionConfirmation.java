package com.tanzakesho.azam;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tanzakesho.framework.FontCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by othmankulindwa on 3/27/16.
 */
public class FragmentSubscriptionConfirmation extends DialogFragment implements View.OnClickListener{

    Button cancelButton;
    Button confirmButton;
    int dWidth;
    int dHeight;

    TextView mPackage;
    TextView cost;
    TextView startDate;
    TextView endDate;

    String mPackageValue;
    String costValue;

    OnChoiceMadeListener mListener;

    public FragmentSubscriptionConfirmation(){

    }

    public static FragmentSubscriptionConfirmation newInstance(int width, int height, String mPackageValue, String costValue){

        Bundle args = new Bundle();
        args.putInt("displayWidth", width);
        args.putInt("displayHeight", height);
        args.putString("package", mPackageValue);
        args.putString("cost", costValue);
        FragmentSubscriptionConfirmation df = new FragmentSubscriptionConfirmation();
        df.setArguments(args);

        return df;
    }


    /**
     * Called to have the fragment instantiate its user interface view.
     * This is optional, and non-graphical fragments can return null (which
     * is the default implementation).  This will be called between
     * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
     * <p/>
     * <p>If you return a View from here, you will later be called in
     * {@link #onDestroyView} when the view is being released.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.confirmation_subscription, container, false);

        setCancelable(false);

        mPackage = (TextView) view.findViewById(R.id.text_package);
        cost = (TextView) view.findViewById(R.id.text_amount);
        startDate = (TextView) view.findViewById(R.id.text_start);
        endDate = (TextView) view.findViewById(R.id.text_end);

        dWidth = getArguments().getInt("displayWidth");
        dHeight = getArguments().getInt("displayHeight");
        mPackageValue = getArguments().getString("package");
        costValue = getArguments().getString("cost");


        Typeface typeface = FontCache.getTypeface("Roboto-Light.ttf", getActivity());

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        cancelButton = (Button) view.findViewById(R.id.channel_cancel);
        ViewGroup.LayoutParams paramsOne = cancelButton.getLayoutParams();
        paramsOne.width = dm.widthPixels/3;
        paramsOne.height = dm.heightPixels/12;
        Log.i("DIMEN", "widht " + String.valueOf(dm.widthPixels)+"and "+ String.valueOf(dm.heightPixels));
        cancelButton.setLayoutParams(paramsOne);
        cancelButton.setMinimumHeight(24);
        cancelButton.setTypeface(typeface);
        cancelButton.setOnClickListener(this);

        confirmButton = (Button) view.findViewById(R.id.channel_confirm);
        ViewGroup.LayoutParams paramsTwo = confirmButton.getLayoutParams();
        paramsTwo.width = dm.widthPixels/3;
        paramsTwo.height = dm.heightPixels/12;
        confirmButton.setLayoutParams(paramsTwo);
        confirmButton.setMinimumHeight(24);
        confirmButton.setTypeface(typeface);
        confirmButton.setOnClickListener(this);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view.setMinimumWidth(dWidth);
        view.setMinimumHeight(dHeight);

        displaySummary();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnChoiceMadeListener){
            mListener = (OnChoiceMadeListener) activity;
        }else{
            throw new RuntimeException(activity.toString()
            + " must implement OnChoiceMadeListener");

        }
    }

    @Override
    public void onClick(View view) {
        if(view == cancelButton){
            mListener.onChoiceMade(false);
            dismiss();
        }
        else{
            mListener.onChoiceMade(true);
            dismiss();
        }
    }

    public interface OnChoiceMadeListener{
        public void onChoiceMade(Boolean choice);
    }

    public void displaySummary(){
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH : mm ");
        String dateString = sdf.format(currentTime);

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE,30 );

        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy/MM/dd HH : mm ");
        String newDateString = newSdf.format(c.getTime());

        mPackage.setText(mPackageValue);
        cost.setText(costValue);
        startDate.setText(dateString);
        endDate.setText(newDateString);
    }
}
