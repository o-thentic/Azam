package com.tanzakesho.azam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.tanzakesho.azam.tabs.AzamAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class FragmentBookingOne extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BOOKING_SCREEN = "bookingScreen";

    // TODO: Rename and change types of parameters

    private OnBookingScreenInteractionListener mListener;
    private GestureDetectorCompat mDetector;

    // Screen One Parameters
    RadioButton radioReturn;
    RadioButton radioOneWay;
    LinearLayout outBound;
    Spinner spinnerCurrency;
    Spinner spinnerFromTo;
    TextView textOutDate;
    Spinner spinnerOutTime;
    EditText editPassengersOut;

    LinearLayout inbound;
    TextView textFromTo;
    TextView textInDate;
    Spinner spinnerInTime;
    EditText editPassengersIn;

    int year_x, month_x, day_x;

    public FragmentBookingOne() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentBookingOne newInstance() {
        FragmentBookingOne fragment = new FragmentBookingOne();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDetector = new GestureDetectorCompat(getContext(), new AzamGestureListener());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public void onForwardSwipe() {
        if (radioReturn.isChecked()) {
            String cur = spinnerCurrency.getSelectedItem().toString();
            String obFT = spinnerFromTo.getSelectedItem().toString();
            String outD = textOutDate.getText().toString();
            String outT = spinnerOutTime.getSelectedItem().toString();
            int outP = Integer.valueOf(editPassengersOut.getText().toString());

            String ibFT = textFromTo.getText().toString();
            String inD = textInDate.getText().toString();
            String inT = spinnerInTime.getSelectedItem().toString();
            int inP = Integer.valueOf(editPassengersIn.getText().toString());



        } else {
            String cur = spinnerCurrency.getSelectedItem().toString();
            String obFT = spinnerFromTo.getSelectedItem().toString();
            String outD = textOutDate.getText().toString();
            String outT = spinnerOutTime.getSelectedItem().toString();
            int outP = Integer.valueOf(editPassengersOut.getText().toString());


        }
    }

    public String dayOfWeek(int day) {
        if (day == 1)
            return "Monday";
        else if (day == 2)
            return "Tuesday";
        else if (day == 3)
            return "Wednesday";
        else if (day == 4)
            return "Thursday";
        else if (day == 5)
            return "Friday";
        else if (day == 6)
            return "Saturday";
        else
            return "Sunday";

    }

    public View initScreen(LayoutInflater inflater, ViewGroup container) {

        View view = inflater.inflate(R.layout.fragment_booking_screen_1, container, false);
        final List<String> arrayInbound = Arrays.asList(getResources().getStringArray(R.array.inbound_from_to));

        Calendar c = Calendar.getInstance();
        year_x = c.get(Calendar.YEAR);
        month_x = c.get(Calendar.MONTH);
        day_x = c.get(Calendar.DATE);
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf");

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dWidth = dm.widthPixels;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyy");
        String currentDate = sdf.format(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDate = DateFormat.format("EEEE", new Date(calendar.getTimeInMillis())).toString() + "  " + currentDate;
        calendar.add(Calendar.DATE, 1);
        String futureDate = DateFormat.format("EEEE", new Date(calendar.getTimeInMillis())).toString() + "  " + sdf.format(calendar.getTime());

        radioReturn = (RadioButton) view.findViewById(R.id.radio_return);
        radioReturn.setTypeface(typeface);

        radioOneWay = (RadioButton) view.findViewById(R.id.radio_oneway);
        radioOneWay.setTypeface(typeface);

        outBound = (LinearLayout) view.findViewById(R.id.outbound);
        ViewGroup.LayoutParams outParams = outBound.getLayoutParams();
        outParams.width = dWidth - (dWidth / 3);
        outBound.setLayoutParams(outParams);

        spinnerCurrency = (Spinner) view.findViewById(R.id.spinner_currency);
        spinnerFromTo = (Spinner) view.findViewById(R.id.outbound_from_to);

        textOutDate = (TextView) view.findViewById(R.id.outbound_date);
        textOutDate.setText(currentDate);

        spinnerOutTime = (Spinner) view.findViewById(R.id.outbound_time);

        editPassengersOut = (EditText) view.findViewById(R.id.outbound_passengers);
        editPassengersOut.setTypeface(typeface);

        inbound = (LinearLayout) view.findViewById(R.id.inbound);
        ViewGroup.LayoutParams inParams = inbound.getLayoutParams();
        inParams.width = dWidth - (dWidth / 3);
        outBound.setLayoutParams(inParams);

        textFromTo = (TextView) view.findViewById(R.id.inbound_from_to);

        textInDate = (TextView) view.findViewById(R.id.inbound_date);
        textInDate.setText(futureDate);

        spinnerInTime = (Spinner) view.findViewById(R.id.inbound_time);

        editPassengersIn = (EditText) view.findViewById(R.id.inbound_passengers);
        editPassengersIn.setTypeface(typeface);

        radioReturn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    inbound.setVisibility(View.VISIBLE);
                else
                    inbound.setVisibility(View.GONE);

            }
        });

        spinnerCurrency.setAdapter(new AzamAdapter(getActivity(),
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.residecy)));

        spinnerFromTo.setAdapter(new AzamAdapter(getActivity(),
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.outbound_from_to)));

        spinnerFromTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                textFromTo.setText(arrayInbound.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year_x = i;
                month_x = i1;
                day_x = i2;
                String d = day_x + "/" + (month_x + 1) + "/" + year_x;
                String sdf = DateFormat.format("EEEE", new Date(year_x, month_x, day_x - 1)).toString();

                d = sdf + "  " + d;
                Log.i("TAG", String.valueOf(datePicker.getId()));
                textOutDate.setText(d);
            }
        };

        textOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(getActivity(), dateSetListener, year_x, month_x, day_x);
                date.show();
            }
        });

        spinnerOutTime.setAdapter(new AzamAdapter(getActivity(),
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.journey_time)));

        textInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(getActivity(), dateSetListener, year_x, month_x, day_x);
                date.show();
            }
        });

        spinnerInTime.setAdapter(new AzamAdapter(getActivity(),
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.journey_time)));

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return mDetector.onTouchEvent(motionEvent);
            }
        });

        return view;
    }

    private class AzamGestureListener extends GestureDetector.SimpleOnGestureListener {


        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (velocityX < 1)
                onForwardSwipe();

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


}
