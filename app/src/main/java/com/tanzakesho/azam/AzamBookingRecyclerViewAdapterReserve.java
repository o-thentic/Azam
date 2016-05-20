package com.tanzakesho.azam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class AzamBookingRecyclerViewAdapterReserve extends RecyclerView.Adapter<AzamBookingRecyclerViewAdapterReserve.ViewHolder> {

    public final List<itemBScreenTwo> mValues;
    int windowWidth;
    private Context context;

    public AzamBookingRecyclerViewAdapterReserve(List<itemBScreenTwo> items, int width, Context context) {
        mValues = items;
        windowWidth = width;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking_summary, parent, false);
        view.setMinimumWidth(windowWidth);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.trip.setText(mValues.get(position).trip);
        if (mValues.get(position).trip.equals("Outbound"))
            holder.ll.setBackgroundColor(holder.outboundColor);
        else
            holder.ll.setBackgroundColor(holder.inboundColor);
        String name = mValues.get(position).firstName + " " + mValues.get(position).lastName;
        holder.passenger.setText(name);
        holder.seat.setText(mValues.get(position).seatType);
        holder.amount.setText(mValues.get(position).seatPrice);
        holder.nat.setText(mValues.get(position).nationality);
        holder.sex.setText(mValues.get(position).gender);
        holder.IDnumber.setText(mValues.get(position).passengerID);
        holder.fromTo.setText(mValues.get(position).fromTo);
        holder.date.setText(mValues.get(position).date);
        holder.Dtime.setText(mValues.get(position).time);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
            super(view);
            outboundColor = context.getResources().getColor(R.color.Blue500);
            inboundColor = context.getResources().getColor((R.color.colorSecondary));
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
