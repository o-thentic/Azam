package com.tanzakesho.azam;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tanzakesho.framework.FontCache;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by othmankulindwa on 3/27/16.
 */
public class FragmentPaymentConfirmation extends DialogFragment implements View.OnClickListener {

    AzamBookingRecyclerViewAdapter mRecycler;

    Button confirmButton;
    Button cancelButton;
    View footerView;
    private double mAmountDue;

    OnChoiceMadeListener mListener;
    List<itemBScreenTwo> mItems;

    public FragmentPaymentConfirmation() {

    }

    public static FragmentPaymentConfirmation newInstance(List<itemBScreenTwo> items, double amountDue) {

        Bundle args = new Bundle();
        FragmentPaymentConfirmation df = new FragmentPaymentConfirmation();
        df.setItems(items, amountDue);
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
        View view = inflater.inflate(R.layout.confirmation_payment, container, false);

        setCancelable(false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        Typeface typeface = FontCache.getTypeface("Roboto-Light.ttf", getActivity());


        confirmButton = (Button) view.findViewById(R.id.booking_confirm);
        ViewGroup.LayoutParams paramsOne = confirmButton.getLayoutParams();
        paramsOne.width = dm.widthPixels / 3;
        paramsOne.height = dm.heightPixels / 12;
        Log.i("DIMEN", "widht " + String.valueOf(dm.widthPixels) + "and " + String.valueOf(dm.heightPixels));
        confirmButton.setLayoutParams(paramsOne);
        confirmButton.setMinimumHeight(24);
        confirmButton.setTypeface(typeface);
        confirmButton.setOnClickListener(this);

        cancelButton = (Button) view.findViewById(R.id.booking_cancel);
        ViewGroup.LayoutParams paramsTwo = cancelButton.getLayoutParams();
        paramsTwo.width = dm.widthPixels / 3;
        paramsTwo.height = dm.heightPixels / 12;
        cancelButton.setLayoutParams(paramsTwo);
        confirmButton.setMinimumHeight(24);
        cancelButton.setTypeface(typeface);
        cancelButton.setOnClickListener(this);

        ListView recyclerView = (ListView) view.findViewById(R.id.list_booking);
        recyclerView.setMinimumWidth(dm.widthPixels);
        mRecycler = new AzamBookingRecyclerViewAdapter(mItems, dm.widthPixels, getActivity(), mListener);
        recyclerView.setAdapter(mRecycler);

        footerView = inflater.inflate(R.layout.fragment_booking_footer, container, false);
        recyclerView.addFooterView(footerView);
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.onItemEditMade(i);
                dismiss();
            }
        });

        TextView textAmount = (TextView) view.findViewById(R.id.text_amountDue);
        TextView textTax = (TextView) view.findViewById(R.id.text_tax);
        TextView textTotal = (TextView) view.findViewById(R.id.text_total);

        textAmount.setText(String.valueOf(mAmountDue));
        Log.i("DUE", String.valueOf(mAmountDue));
        textTax.setText(String.valueOf(taxCalc(mAmountDue)));
        Log.i("DUE", String.valueOf(taxCalc(mAmountDue)));
        textTotal.setText(String.valueOf(totalCalc(mAmountDue, taxCalc(mAmountDue))));

        view.setMinimumWidth(dm.widthPixels);
        view.setMinimumHeight(dm.heightPixels);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnChoiceMadeListener) {
            mListener = (OnChoiceMadeListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnChoiceMadeListener");

        }
    }

    @Override
    public void onClick(View view) {
        if (view == cancelButton) {
            mListener.onChoiceMade(false);
            dismiss();
        } else {
            mListener.onChoiceMade(true);
            dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mItems.clear();
        mRecycler.mValues.clear();
        super.onDismiss(dialog);
    }

    public interface OnChoiceMadeListener {
        void onChoiceMade(Boolean choice);
        void onItemEditMade(int position);
    }

    public void displaySummary(View view) {

    }

    public void setItems(List<itemBScreenTwo> items, double amoutDue) {
        mItems = items;
        mAmountDue = amoutDue;
    }

    public double taxCalc(double amount) {
        double tax = amount * (8.00 / 100.00);
        return tax;
    }

    public double totalCalc(double a, double t) {
        return a + t;
    }
}
