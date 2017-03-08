package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;
import com.plusonesoftwares.plusonesoftwares.bignews.unit.AphidLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class CustomViewAdapter extends ArrayAdapter<Travels.Data> {

    Context context;
    private List<Travels.Data> travelData;
    JSONArray jsonArray;
    CustomViewAdapter.ViewHolder holder= null;
    JSONObject jObject;

    public CustomViewAdapter(Context context) {
        super(context, R.layout.new_item);
        this.context = context;
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
    }
    public CustomViewAdapter(Context context, JSONArray jsonArray) {
        super(context, R.layout.new_item);
        this.context = context;
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        this.jsonArray = jsonArray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new CustomViewAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.new_item, null, true);
            final Travels.Data data = travelData.get(position % travelData.size());

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.title_image = (ImageView) convertView.findViewById(R.id.titleimage);




            try {
                jObject = jsonArray.getJSONObject(position);

               holder.title.setText(AphidLog.format(jObject.getString("title")));
               new ImageDownloader(holder.title_image).execute(jObject.getString("imgURL"));
               // jObject.getString("imgURL");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            holder = (CustomViewAdapter.ViewHolder) convertView.getTag();
        }
        //jObject.getString("imgURL")

        return convertView;
    }


    @Override
    public int getCount() {
        return 4;
    }

    private class ViewHolder{
        TextView title;
        ImageView title_image;
    }
}
