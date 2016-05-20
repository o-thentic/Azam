package com.tanzakesho.azam;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanzakesho.azam.FragmentItem.OnListFragmentInteractionListener;
import com.tanzakesho.azam.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AzamItemRecyclerViewAdapter extends RecyclerView.Adapter<AzamItemRecyclerViewAdapter.ViewHolder> {

    final int ITEM_WIDTH = 121;
    final int ITEM_HEIGHT = 72;

    public final List<ItemCategory> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final String mCategory;

    public AzamItemRecyclerViewAdapter(List<ItemCategory> items, OnListFragmentInteractionListener listener, String category) {
        mValues = items;
        mListener = listener;
        mCategory = category;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_fragment, parent, false);

        int dHeight = parent.getHeight();
        int dWidth = parent.getWidth();

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (dWidth*ITEM_HEIGHT)/ITEM_WIDTH;
        view.setLayoutParams(params);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitle.setText(mValues.get(position).heading);
        holder.mContentView.setImageBitmap(mValues.get(position).bitmap);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mTitle;
        public final ImageView mContentView;
        public ItemCategory mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.id);
            mContentView = (ImageView) view.findViewById(R.id.content);
        }


    }
}
