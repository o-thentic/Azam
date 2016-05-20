package com.tanzakesho.azam;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FragmentItem extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    public static final String ARG_CATEGORY = "product_category";
    public static boolean busy = false;
    public static int taskCount = 0;


    // TODO: Customize parameters
    private AzamItemRecyclerViewAdapter azamAdapter;
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private static Context mContext;

    public String mCategory;
    public final List<ItemCategory> itemList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentItem() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static FragmentItem newInstance(int columnCount, String category, Context context) {
        FragmentItem fragment = new FragmentItem();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        mContext = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mCategory = getArguments().getString(ARG_CATEGORY);
        startTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            azamAdapter = new AzamItemRecyclerViewAdapter(itemList, mListener, mCategory);
            recyclerView.setAdapter(azamAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(ItemCategory item);
    }

    public void startTask() {
        if (itemList.size() < 1) {

            new CategoryItemAsyncTask(0, 3).execute("http://10.0.2.2:2403/specificsgroup");
            if (!busy)
                busy = true;
        }
    }

    public class CategoryItemAsyncTask extends AsyncTask<String, Void, String> {
        String UrlString;
        String GetCommand;
        int from;
        int to;
        URL url;
        InputStream is;
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();

        public CategoryItemAsyncTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected String doInBackground(String... strings) {

            String cat = "";
            if (mCategory.equals(Home.AZAMTV_CAT))
                cat = "Azam%20TV";
            else if (mCategory.equals(Home.AZAMMARINE_CAT))
                cat = "Azam%20Marine";
            else
                cat = "Food&Drinks";
            UrlString = strings[0];
            GetCommand = "?{\"$skip\":" + from + ",\"$limit\":" + to + ",\"specificsgroupset\":{\"$in\":[\"" + cat + "\"]}}";


            try {

                url = new URL(UrlString + GetCommand);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();

                is = connection.getInputStream();
                System.out.println("The screen " + is);

                String line;

                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                if (null != (line = reader.readLine())) {
                    sb.append(line);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null)
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject mObject;
            String title;
            String image;
            String groupset;
            String id;
            try {
                JSONArray mArray = new JSONArray(s);
                for (int i = 0; i < mArray.length(); i++) {
                    mObject = mArray.getJSONObject(i);
                    title = mObject.getString("name");
                    image = mObject.getString("image");
                    groupset = mObject.getString("specificsgroupset");
                    id = mObject.getString("id");

                    azamAdapter.mValues.add(new ItemCategory(title, image, id, groupset));
                    azamAdapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            busy = false;
            taskCount += 1;
        }
    }
}
