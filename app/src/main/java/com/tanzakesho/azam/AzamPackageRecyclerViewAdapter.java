package com.tanzakesho.azam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tanzakesho.azam.FragmentItem.OnListFragmentInteractionListener;
import com.tanzakesho.azam.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AzamPackageRecyclerViewAdapter extends RecyclerView.Adapter<AzamPackageRecyclerViewAdapter.ViewHolder> {

    public final List<ItemPackage> mValues;
    private Context mContext;

    public AzamPackageRecyclerViewAdapter(List<ItemPackage> items, Context mContext) {
        mValues = items;
        this.mContext = mContext;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_package, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.i("myInfoBro", mValues.get(position).mChannels.toString());

        holder.mItem = mValues.get(position);
        holder.mTitleOne.setText(holder.mItem.mTitleOne);
        holder.mTitleTwo.setText(holder.mItem.mTitleTwo);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("tOne", holder.mItem.mTitleOne);
                bundle.putString("tTwo", holder.mItem.mTitleTwo);
                bundle.putString("cost", holder.mItem.mCost);
                String[] c = holder.mItem.mChannels.toArray(new String[holder.mItem.mChannels.size()]);

                bundle.putStringArray("channels",c );

                Intent intent = new Intent(mContext, Channels.class);
                intent.putExtra("extra", bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleOne;
        public final TextView mTitleTwo;
        public ItemPackage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleOne = (TextView) view.findViewById(R.id.package_item_text1);
            mTitleTwo = (TextView) view.findViewById(R.id.package_item_text2);
        }


    }

}
