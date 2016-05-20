package com.tanzakesho.azam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tanzakesho.azam.tabs.AzamAdapter;
import com.tanzakesho.framework.AzamBaseNav;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Booking extends AzamBaseNav implements View.OnTouchListener {

    private GestureDetectorCompat mGesture;

    public static final String BOOKING_NFO = "booking_info";

    public static final String RETURN = "return";
    public static final String CURRENCY = "currency";
    public static final String OUTBOUND_TRIP = "outbound_trip";
    public static final String OUTBOUND_DATE = "outbound_date";
    public static final String OUTBOUND_TIME = "outbound_time";
    public static final String OUTBOUND_PASSENGERS = "outbound_passengers";

    public static final String INBOUND_TRIP = "inbound_trip";
    public static final String INBOUND_DATE = "inbound_date";
    public static final String INBOUND_TIME = "inbound_time";
    public static final String INBOUND_PASSENGERS = "inbound_passengers";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initScreen();
        mGesture = new GestureDetectorCompat(this, new AzamGestureListener());
        View view = findViewById(R.id.drawer_layout_booking);
        view.setOnTouchListener(this);

        //View sv = findViewById(R.id.booking_scroll);
        //sv.setOnTouchListener(this);
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
        super.initVariables(R.layout.activity_booking, R.id.toolbar_booking, R.id.booking_title, R.id.drawer_layout_booking, "Roboto-Light.ttf");
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGesture.onTouchEvent(motionEvent);
    }

    public void initScreen() {


        final List<String> arrayInbound = Arrays.asList(getResources().getStringArray(R.array.inbound_from_to));

        Calendar c = Calendar.getInstance();
        year_x = c.get(Calendar.YEAR);
        month_x = c.get(Calendar.MONTH);
        day_x = c.get(Calendar.DATE);
        Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "Roboto-Light.ttf");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
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

        radioReturn = (RadioButton) findViewById(R.id.radio_return);
        radioReturn.setTypeface(typeface);

        radioOneWay = (RadioButton) findViewById(R.id.radio_oneway);
        radioOneWay.setTypeface(typeface);

        outBound = (LinearLayout) findViewById(R.id.outbound);
        ViewGroup.LayoutParams outParams = outBound.getLayoutParams();
        outParams.width = dWidth - (dWidth / 3);
        outBound.setLayoutParams(outParams);

        spinnerCurrency = (Spinner) findViewById(R.id.spinner_currency);
        spinnerFromTo = (Spinner) findViewById(R.id.outbound_from_to);

        textOutDate = (TextView) findViewById(R.id.outbound_date);
        textOutDate.setText(currentDate);

        spinnerOutTime = (Spinner) findViewById(R.id.outbound_time);

        editPassengersOut = (EditText) findViewById(R.id.outbound_passengers);
        editPassengersOut.setTypeface(typeface);

        inbound = (LinearLayout) findViewById(R.id.inbound);
        ViewGroup.LayoutParams inParams = inbound.getLayoutParams();
        inParams.width = dWidth - (dWidth / 3);
        outBound.setLayoutParams(inParams);

        textFromTo = (TextView) findViewById(R.id.inbound_from_to);

        textInDate = (TextView) findViewById(R.id.inbound_date);
        textInDate.setText(futureDate);

        spinnerInTime = (Spinner) findViewById(R.id.inbound_time);

        editPassengersIn = (EditText) findViewById(R.id.inbound_passengers);
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

        spinnerCurrency.setAdapter(new AzamAdapter(this,
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.residecy)));

        spinnerFromTo.setAdapter(new AzamAdapter(this,
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
                DatePickerDialog date = new DatePickerDialog(getApplicationContext(), dateSetListener, year_x, month_x, day_x);
                date.show();
            }
        });

        spinnerOutTime.setAdapter(new AzamAdapter(this,
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.journey_time)));

        textInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog date = new DatePickerDialog(getApplicationContext(), dateSetListener, year_x, month_x, day_x);
                date.show();
            }
        });

        spinnerInTime.setAdapter(new AzamAdapter(this,
                R.layout.item_list_spinner_text, getResources().getStringArray(R.array.journey_time)));



    }

    public void onForwardSwipe() {
        if (radioReturn.isChecked()) {
            boolean returnTrip = radioReturn.isChecked();
            String cur = spinnerCurrency.getSelectedItem().toString();
            String obFT = spinnerFromTo.getSelectedItem().toString();
            String outD = textOutDate.getText().toString();
            String outT = spinnerOutTime.getSelectedItem().toString();
            int outP = Integer.valueOf(editPassengersOut.getText().toString());

            String ibFT = textFromTo.getText().toString();
            String inD = textInDate.getText().toString();
            String inT = spinnerInTime.getSelectedItem().toString();
            int inP = Integer.valueOf(editPassengersIn.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putBoolean(RETURN, returnTrip);
            bundle.putString(CURRENCY, cur);

            bundle.putString(OUTBOUND_TRIP, obFT);
            bundle.putString(OUTBOUND_DATE, outD);
            bundle.putString(OUTBOUND_TIME, outT);
            bundle.putInt(OUTBOUND_PASSENGERS, outP);

            bundle.putString(INBOUND_TRIP, ibFT);
            bundle.putString(INBOUND_DATE, inD);
            bundle.putString(INBOUND_TIME, inT);
            bundle.putInt(INBOUND_PASSENGERS, inP);

            Intent intent = new Intent(Booking.this, BookingFin.class);
            intent.putExtra(BOOKING_NFO, bundle);
            startActivity(intent);


        } else {
            boolean returnTrip = radioReturn.isChecked();
            String cur = spinnerCurrency.getSelectedItem().toString();
            String obFT = spinnerFromTo.getSelectedItem().toString();
            String outD = textOutDate.getText().toString();
            String outT = spinnerOutTime.getSelectedItem().toString();
            int outP = Integer.valueOf(editPassengersOut.getText().toString());

            Bundle bundle = new Bundle();
            bundle.putBoolean(RETURN, returnTrip);
            bundle.putString(CURRENCY, cur);

            bundle.putString(OUTBOUND_TRIP, obFT);
            bundle.putString(OUTBOUND_DATE, outD);
            bundle.putString(OUTBOUND_TIME, outT);
            bundle.putInt(OUTBOUND_PASSENGERS, outP);

            Intent intent = new Intent(Booking.this, BookingFin.class);
            intent.putExtra(BOOKING_NFO, bundle);
            startActivity(intent);
        }

        finish();
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
