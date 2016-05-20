package com.tanzakesho.azam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tanzakesho.azam.FragmentItem.OnListFragmentInteractionListener;
import com.tanzakesho.azam.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AzamBookingRecyclerViewAdapter extends BaseAdapter {

    FragmentPaymentConfirmation.OnChoiceMadeListener listener;
    public final List<itemBScreenTwo> mValues;
    int windowWidth;
    private Context context;

    public AzamBookingRecyclerViewAdapter(List<itemBScreenTwo> items, int width, Context context, FragmentPaymentConfirmation.OnChoiceMadeListener listener) {
        mValues = items;
        windowWidth = width;
        this.context = context;
        this.listener = listener;
    }


    public void processViews(int position, ViewHolder holder) {
        itemBScreenTwo screenItem = (itemBScreenTwo) getItem(position);
        holder.trip.setText(screenItem.trip);
        if (screenItem.trip.equals("Outbound"))
            holder.ll.setBackgroundColor(holder.outboundColor);
        else
            holder.ll.setBackgroundColor(holder.inboundColor);
        String name = screenItem.firstName + " " + screenItem.lastName;
        holder.passenger.setText(name);
        holder.seat.setText(screenItem.seatType);
        holder.amount.setText(screenItem.seatPrice);
        holder.nat.setText(screenItem.nationality);
        holder.sex.setText(screenItem.gender);
        holder.IDnumber.setText(screenItem.passengerID);
        holder.fromTo.setText(screenItem.fromTo);
        holder.date.setText(screenItem.date);
        holder.Dtime.setText(screenItem.time);
    }


    @Override
    public int getCount() {
        return mValues.size();
    }

    @Override
    public Object getItem(int i) {
        return mValues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder mHolder = null;
        View mView = view;

        if (mView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflater.inflate(R.layout.item_booking_summary, viewGroup, false);
            mHolder = new ViewHolder(mView);
            mView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) mView.getTag();
        }

        processViews(i, mHolder);

        return mView;
    }

    public class ViewHolder {
        public final LinearLayout ll;
        public final int outboundColor;
        public final int inboundColor;
        public final TextView trip;
        public final TextView passenger;
        public final TextView seat;
        public final TextView amount;
        public final TextView nat;
        public final TextView sex;
        public final TextView IDnumber;
        public final TextView fromTo;
        public final TextView date;
        public final TextView Dtime;

        public ViewHolder(View view) {

            inboundColor = context.getResources().getColor(R.color.Blue500);
            outboundColor = context.getResources().getColor((R.color.colorSecondary));
            ll = (LinearLayout) view.findViewById(R.id.booking_title_back);
            trip = (TextView) view.findViewById(R.id.summary_trip);
            passenger = (TextView) view.findViewById(R.id.summary_passenger_name);
            seat = (TextView) view.findViewById(R.id.summary_seat_value);
            amount = (TextView) view.findViewById(R.id.summary_amnt_value);
            nat = (TextView) view.findViewById(R.id.summary_nat_value);
            sex = (TextView) view.findViewById(R.id.summary_sex_value);
            fromTo = (TextView) view.findViewById(R.id.summary_from_to_value);
            IDnumber = (TextView) view.findViewById(R.id.summary_passenger_id_value);
            date = (TextView) view.findViewById(R.id.summary_trip_date_value);
            Dtime = (TextView) view.findViewById(R.id.summary_departure);

        }

    }
}
