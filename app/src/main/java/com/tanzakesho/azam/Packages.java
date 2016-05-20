package com.tanzakesho.azam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tanzakesho.framework.AzamBaseNav;

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

public class Packages extends AzamBaseNav {

    private AzamPackageRecyclerViewAdapter packageAdapter;
    public final List<ItemPackage> itemList = new ArrayList<>();
    private static boolean confirmed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list_content);
        packageAdapter = new AzamPackageRecyclerViewAdapter(itemList, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(packageAdapter);
        new PackageItemAsyncTask(0, 10).execute("http://10.0.2.2:2403/specifics");

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
    protected void onResume() {
        super.onResume();
        if(confirmed){
            confirmed = false;
            finish();
        }
    }

    public static void endActivity(){
        confirmed = true;
    }

    @Override
    public void initVariables(int mContentViewId, int mToolBarId, int mTitleTextId, int mDrawerId, String mTypeFace) {
        super.initVariables(R.layout.activity_package, R.id.toolbar_package, R.id.package_title, R.id.drawer_layout_package, "Roboto-Light.ttf");
    }

    public class PackageItemAsyncTask extends AsyncTask<String, Void, String> {
        String UrlString;
        String GetCommand;
        int from;
        int to;
        URL url;
        InputStream is;
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();

        public PackageItemAsyncTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        protected String doInBackground(String... strings) {

            String cat = "AZAM%20PACKAGES";
            UrlString = strings[0];
            GetCommand = "?{\"$skip\":" + from + ",\"$limit\":" + to + ",\"specificsgroup\":{\"$in\":[\"" + cat + "\"]}}";


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
            String titleOne;
            String titleTwo;
            String groupset;
            String cost;
            String id;

            try {
                JSONArray mArray = new JSONArray(s);
                for (int i = 0; i < mArray.length(); i++) {
                    List<String> mContent = new ArrayList<>();
                    mObject = mArray.getJSONObject(i);
                    titleOne = mObject.getString("name");
                    titleTwo = mObject.getString("description");
                    cost = mObject.getString("cost");
                    if(mObject.getJSONArray("content").length()>0){
                        for (int j = 0; j < mObject.getJSONArray("content").length(); j++) {
                            mContent.add(mObject.getJSONArray("content").getString(j));
                        }

                    }
                    groupset = mObject.getString("specificsgroup");
                    id = mObject.getString("id");
                    packageAdapter.mValues.add(new ItemPackage(titleOne, titleTwo, id, mContent, groupset, cost));
                    packageAdapter.notifyDataSetChanged();
                    //mContent.clear();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}
