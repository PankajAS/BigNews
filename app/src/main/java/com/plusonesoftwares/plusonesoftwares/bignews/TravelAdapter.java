package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class TravelAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;

    private int repeatCount = 1;
    private List<Travels.Data> travelData;
    JSONArray jarray;

    public TravelAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
    }

    public TravelAdapter(Context context, JSONArray jarray) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        this.jarray = jarray;
    }
    @Override
    public int getCount() {
        //return travelData.size() * repeatCount;
        return 2;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View layout = convertView;
        if (convertView == null) {
            layout = inflater.inflate(R.layout.activity_home_fragment, null);
            AphidLog.d("created new view from adapter: %d", position);
        }

        final Travels.Data data = travelData.get(position % travelData.size());

        UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context, jarray));

        UI.<ListView>findViewById(layout, R.id.list).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    JSONObject jobject = jarray.getJSONObject(i);
                    Intent intent = new Intent(context, NewsDetails.class);
                    intent.putExtra("Data",jobject.toString());
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return layout;
    }

    public List<Travels.Data> getTravelData() {
        return travelData;
    }
}
