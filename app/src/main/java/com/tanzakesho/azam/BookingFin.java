package com.tanzakesho.azam;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.commonsware.cwac.pager.ArrayPagerAdapter;
import com.commonsware.cwac.pager.PageDescriptor;
import com.commonsware.cwac.pager.SimplePageDescriptor;
import com.tanzakesho.framework.AzamBaseNav;

import java.util.ArrayList;
import java.util.List;

public class BookingFin extends AzamBaseNav implements OnBookingScreenInteractionListener,
        FragmentPaymentConfirmation.OnChoiceMadeListener, FragmentSubmittButton.OnSubmitInteractionListener,
        FragmentBookingTwo.OnPassengerDeleteListener {

    private String INTENT_CONF = "com.tanzakesho.PAYMENT_CONF";

    private ViewPager mPager;
    private FragmentManager mManager;
    private int pageNumber = 1;
    private int countOut;
    private int countIn;
    private double amountDue = 0.00;

    ArrayList<PageDescriptor> pages;
    private List<itemBScreenTwo> mPassengers = new ArrayList<>();
    private com.commonsware.cwac.pager.v4.ArrayPagerAdapter<Fragment> mAdapter = null;

    String currency, obTrip, outD, outT;
    String ibTrip, ibD, ibT;
    public int outP, inP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pages = new ArrayList<>();
        Bundle bundle = getIntent().getBundleExtra(Booking.BOOKING_NFO);
        if (bundle.getBoolean(Booking.RETURN)) {
            currency = bundle.getString(Booking.CURRENCY);
            obTrip = bundle.getString(Booking.OUTBOUND_TRIP);
            outD = bundle.getString(Booking.OUTBOUND_DATE);
            outT = bundle.getString(Booking.OUTBOUND_TIME);
            outP = bundle.getInt(Booking.OUTBOUND_PASSENGERS);

            for (int i = 0; i < outP; i++) {
                //mFragments.add(FragmentBookingTwo.newInstance(currency, "Outbound", outD, outT, obTrip));
                pages.add(new SimplePageDescriptor(buildTag(i), buildTitle("Outbound")));
            }

            ibTrip = bundle.getString(Booking.INBOUND_TRIP);
            ibD = bundle.getString(Booking.INBOUND_DATE);
            ibT = bundle.getString(Booking.INBOUND_TIME);
            inP = bundle.getInt(Booking.INBOUND_PASSENGERS);

            for (int i = outP; i < outP + inP; i++) {
                pages.add(new SimplePageDescriptor(buildTag(i), buildTitle("Inbound")));
            }
            pages.add(new SimplePageDescriptor(buildTag(outP + inP), ("SubmitButton")));


        } else {
            currency = bundle.getString(Booking.CURRENCY);
            obTrip = bundle.getString(Booking.OUTBOUND_TRIP);
            outD = bundle.getString(Booking.OUTBOUND_DATE);
            outT = bundle.getString(Booking.OUTBOUND_TIME);
            outP = bundle.getInt(Booking.OUTBOUND_PASSENGERS);

            for (int i = 0; i < outP; i++) {
                pages.add(new SimplePageDescriptor(buildTag(i), buildTitle("Outbound")));
            }
            pages.add(new SimplePageDescriptor(buildTag(outP), ("SubmitButton")));
        }

        mPager = (ViewPager) findViewById(R.id.pager_booking);
        mManager = getSupportFragmentManager();
        mAdapter = new BookingPagerAdapter(mManager, pages);
        mPager.setAdapter(mAdapter);
        Log.i("TAG", String.valueOf(countOut) + " " + String.valueOf(countIn));

        countOut = outP;
        countIn = inP;
        Log.i("TAG", String.valueOf(countOut) + " " + String.valueOf(countIn));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_booking, menu);
        return true;
    }

    @Override
    protected void onResume() {
        //registerReceiver(new MessageReceiver(), new IntentFilter(INTENT_CONF));

        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_outbound) {
            PageDescriptor pd = null;
            for (int i = 0; i < mAdapter.getCount() - 1; i++) {
                String title = mAdapter.getPageTitle(i);
                Log.i("TAG", title);
                if (title.equals("Inbound")) {
                    Log.i("TAG", "One");
                    pd = new SimplePageDescriptor(buildTag(mAdapter.getCount() + 1), buildTitle("Outbound"));
                    mAdapter.insert(pd, i);
                    outP += 1;
                    countOut = outP;
                    mPager.setCurrentItem(i);
                    break;
                }
                Log.i("TAG", "Two");
            }
            if (pd == null) {
                mAdapter.insert(new SimplePageDescriptor(buildTag(mAdapter.getCount() + 1), buildTitle("Outbound")), mAdapter.getCount() - 1);
                outP += 1;
                countOut = outP;
                mPager.setCurrentItem(mAdapter.getCount() - 2);
            }
        } else {
            mAdapter.insert(new SimplePageDescriptor(buildTag(mAdapter.getCount() + 1), buildTitle("Inbound")), mAdapter.getCount() - 1);
            inP += 1;
            countIn = inP;
            mPager.setCurrentItem(mAdapter.getCount() - 2);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void initVariables(int mContentViewId, int mToolBarId, int mTitleTextId, int mDrawerId,
                              String mTypeFace) {
        super.initVariables(R.layout.activity_bookingfin, R.id.toolbar_bookingfin,
                R.id.bookingfin_title, R.id.drawer_layout_bookingfin, "Roboto-Light.ttf");
    }

    @Override
    public void onBookingInteraction(itemBScreenTwo itemTwo) {
        // TODO executed to pack the chosen item in the FragmentBookingTwo class
        Log.i("TAG", String.valueOf(countOut) + " " + String.valueOf(countIn));
        mPassengers.add(itemTwo);
        if (countOut != 0)
            countOut = Math.max(countOut - 1, 0);
        else if (countIn != 0)
            countIn = Math.max(countIn - 1, 0);

        if (countOut == 0 && countIn == 0) {

            FragmentPaymentConfirmation pc = FragmentPaymentConfirmation.newInstance(mPassengers, amountDue);
            FragmentManager fm = getSupportFragmentManager();
            pc.show(fm, null);
        }
        Log.i("TAG", String.valueOf(countOut) + " " + String.valueOf(countIn));

    }

    @Override
    public void onChoiceMade(Boolean choice) {
        if (choice) {
            for (int i = 0; i < mAdapter.getCount(); i++)
                mAdapter.remove(i);
            mPassengers.clear();
            countOut = 0;
            countIn = 0;
            broadcastConf();
            finish();
        } else {
            countOut = outP;
            countIn = inP;
            mPassengers.clear();
            amountDue = 0;
        }
    }

    @Override
    public void onItemEditMade(int position) {
        countOut = outP;
        countIn = inP;
        mPassengers.clear();
        amountDue = 0;
        mPager.setCurrentItem(position);
    }

    @Override
    public void onSubmitInteraction() {
        // TODO: invoke the onFinishz() method for each fragment present in the adapter
        for (int i = 0; i < mAdapter.getCount() - 1; i++) {
            FragmentBookingTwo bk = (FragmentBookingTwo) mAdapter.getExistingFragment(i);
            amountDue += Double.valueOf(bk.textAmount.getText().toString());
            Log.i("DOUBLE", bk.textAmount.toString());
            bk.onFinish();
        }
    }

    @Override
    public void onPassengerDelete(String tripDescription) {
        // TODO: remove the intended fragment from the adapter
        final int curItem = mPager.getCurrentItem();
        if (mAdapter.getCount() > 2) {

            if (curItem != 0)
                mPager.setCurrentItem(curItem - 1);
            else
                mPager.setCurrentItem(curItem + 1);
            new Thread(new Runnable() {
                final int totalCount = 500;
                int count = 0;

                @Override
                public void run() {
                    while (count < totalCount) {
                        try {
                            Thread.sleep(100);
                            count += 100;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (count >= totalCount) {

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter.remove(curItem);
                                    }
                                });
                            }

                        }
                    }
                }

            }).start();


            if (tripDescription.equals("INBOUND")) {
                inP -= 1;
                countIn = inP;
            } else {
                outP -= 1;
                countOut = outP;
            }
        }
    }

    public void broadcastConf() {
        Intent intent = new Intent();
        intent.setAction(INTENT_CONF);
        sendBroadcast(intent);
    }

    private String buildTag(int position) {
        return ("editor" + String.valueOf(pageNumber++));
    }

    private String buildTitle(String title) {
        return title;
    }

    class BookingPagerAdapter extends com.commonsware.cwac.pager.v4.ArrayPagerAdapter<Fragment> {

        public BookingPagerAdapter(FragmentManager fm, ArrayList<PageDescriptor> desc) {
            super(fm, desc);
        }

        @Override
        protected Fragment createFragment(PageDescriptor pageDescriptor) {
            String t = pageDescriptor.getTitle();
            if (t.equals("SubmitButton"))
                return new FragmentSubmittButton();
            else if (t.equals("Outbound"))
                return FragmentBookingTwo.newInstance(currency, t, outD, outT, obTrip);
            else
                return FragmentBookingTwo.newInstance(currency, t, ibD, ibT, ibTrip);
        }

    }

}
