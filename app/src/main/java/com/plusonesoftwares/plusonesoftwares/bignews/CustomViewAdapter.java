package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class CustomViewAdapter extends ArrayAdapter<Travels.Data> {

    Context context;
    private List<Travels.Data> travelData;

    public CustomViewAdapter(Context context) {
        super(context, R.layout.new_item);
        this.context = context;
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView= inflater.inflate(R.layout.new_item, null, true);
        final Travels.Data data = travelData.get(position % travelData.size());
        TextView txt = (TextView)rowView.findViewById(R.id.title);

        txt.setText(AphidLog.format("%d. %s", position, data.title));

        return rowView;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
