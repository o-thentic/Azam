package com.tanzakesho.azam;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tanzakesho.azam.tabs.AzamAdapter;
import com.tanzakesho.framework.FontCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FragmentBookingTwo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String BOOKING_SCREEN_ITEM = "bookingScreenItem";
    public static final String FRAGMENT_ID = "fragmentID";

    // TODO: Rename and change types of parameters
    private static final String CURRENCY = "cerrency";
    private static final String DATE = "date";
    private static final String TIME = "time";
    private static final String TRIP = "trip";
    private static final String FROMTO = "fromto";

    private OnPassengerDeleteListener dListener;
    private OnBookingScreenInteractionListener mListener;
    private List<String> seatPrices;

    String tripDescrption;

    String mCurrency;
    String mTrip;
    String mFromTo;
    String mDate;
    String mTime;

    // Screen Two Parameters
    Spinner spinnerSeatType;
    TextView textAmount;
    EditText editFirstName;
    EditText editLastName;
    Spinner spinnerNationality;
    Spinner spinnerGender;
    EditText editID;
    EditText editDisabilityInfo;

    public FragmentBookingTwo() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentBookingTwo newInstance(String currency, String trip, String date, String time, String fromTo) {
        FragmentBookingTwo fragment = new FragmentBookingTwo();
        Bundle args = new Bundle();
        args.putString(CURRENCY, currency);
        args.putString(TRIP, trip);
        args.putString(DATE, date);
        args.putString(TIME, time);
        args.putString(FROMTO, fromTo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        if( getArguments() != null){
            Bundle args = getArguments();
            mCurrency = args.getString(CURRENCY);
            mTrip = args.getString(TRIP);
            mDate = args.getString(DATE);
            mTime = args.getString(TIME);
            mFromTo = args.getString(FROMTO);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initScreen(inflater, container);
        return initScreen(inflater, container);
    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBookingScreenInteractionListener) {
            mListener = (OnBookingScreenInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookingScreenInteractionListener");
        }

        if (context instanceof OnPassengerDeleteListener) {
            dListener = (OnPassengerDeleteListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBookingScreenInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onFinish() {
        String trip = mTrip;
        String seatT = spinnerSeatType.getSelectedItem().toString();
        String price = textAmount.getText().toString();
        String fName = editFirstName.getText().toString();
        String lName = editLastName.getText().toString();
        String nat = spinnerNationality.getSelectedItem().toString();
        String gend = spinnerGender.getSelectedItem().toString();
        String passID = editID.getText().toString();
        String tripft = mFromTo;
        String disInfo = editDisabilityInfo.getText().toString();
        String date = mDate;
        String time = mTime;


        mListener.onBookingInteraction(new itemBScreenTwo(trip, seatT, price, fName, lName, nat,
                gend, passID, tripft, disInfo, date, time));

    }

    public View initScreen(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_booking_screen_2, container, false);
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.booking_screen2_main);
        ViewGroup.LayoutParams params = ll.getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        params.width = displayMetrics.widthPixels - (displayMetrics.widthPixels / 3);
        ll.setLayoutParams(params);

        Typeface typeface = FontCache.getTypeface("Roboto-Light.ttf", getActivity());

        if (mCurrency.equals("Resident")) {
            seatPrices = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.seat_price_local)));
        } else {
            seatPrices = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.seat_price_foreign)));
        }

        View headerView = view.findViewById(R.id.booking_header);
        TextView headerText = (TextView) view.findViewById(R.id.booking_header_text);
        textAmount = (TextView) view.findViewById(R.id.fee_amount);
        if(mTrip.equals("Outbound")){
            headerView.setBackgroundColor(getResources().getColor(R.color.colorSecondary));
            tripDescrption = "OUTBOUND";
            headerText.setText(tripDescrption);
        }else{
            headerView.setBackgroundColor(getResources().getColor(R.color.Blue500));
            tripDescrption = "INBOUND";
            headerText.setText(tripDescrption);
        }

        ImageView buttonImage = (ImageView) view.findViewById(R.id.button_delete);
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dListener.onPassengerDelete(tripDescrption);
            }
        });

        spinnerSeatType = (Spinner) view.findViewById(R.id.spinner_seat);
        spinnerSeatType.setAdapter(new AzamAdapter(getActivity(), R.layout.item_list_spinner_text,
                getResources().getStringArray(R.array.seat_type)));
        spinnerSeatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textAmount.setText(seatPrices.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editFirstName = (EditText) view.findViewById(R.id.name_first);
        editFirstName.setTypeface(typeface);

        editLastName = (EditText) view.findViewById(R.id.name_last);
        editLastName.setTypeface(typeface);

        spinnerNationality = (Spinner) view.findViewById(R.id.nationality);
        spinnerNationality.setAdapter(new AzamAdapter(getActivity(), R.layout.item_list_spinner_text, getResources().getStringArray(R.array.countries)));

        spinnerGender = (Spinner) view.findViewById(R.id.gender);
        spinnerGender.setAdapter(new AzamAdapter(getActivity(), R.layout.item_list_spinner_text, getResources().getStringArray(R.array.gender)));

        editID = (EditText) view.findViewById(R.id.passenger_id);
        editID.setTypeface(typeface);

        editDisabilityInfo = (EditText) view.findViewById(R.id.disablity_info);
        editDisabilityInfo.setTypeface(typeface);

        return view;

    }

    public interface OnPassengerDeleteListener{
        void onPassengerDelete(String tripDescription);
    }

}
