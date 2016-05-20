package com.tanzakesho.azam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tanzakesho.azam.FragmentItem.OnListFragmentInteractionListener;
import com.tanzakesho.azam.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AzamChannelsRecyclerViewAdapter extends RecyclerView.Adapter<AzamChannelsRecyclerViewAdapter.ViewHolder> {

    public final List<String> mValues;
    private OnListChannelInteractionListener mListener;

    public AzamChannelsRecyclerViewAdapter(List<String> items) {
        mValues = items;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_channel, parent, false);

        int w = parent.getWidth();
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (w*5)/27;
        view.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.channel.setText(holder.mItem);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListChannelInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView channel;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            channel = (TextView) view.findViewById(R.id.channel_item);
        }


    }

    public interface OnListChannelInteractionListener{
        void onListChannelInteraction(String item);
    }
}
