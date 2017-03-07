package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.UI;

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

    public TravelAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
    }

    @Override
    public int getCount() {
        //return travelData.size() * repeatCount;
        return travelData.size()/3;
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

        UI.<ListView>findViewById(layout, R.id.list).setAdapter(new CustomViewAdapter(context));
        UI.<ListView>findViewById(layout,R.id.list).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(i);
            }
        });

        return layout;
    }
}
