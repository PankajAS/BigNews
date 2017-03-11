package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusonesoftwares.plusonesoftwares.bignews.data.Travels;

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
    Activity parentContext;

    public CustomViewAdapter(Context context) {
        super(context, R.layout.new_item);
        this.context = context;
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
    }

    public CustomViewAdapter(Context context,Activity parentContext, JSONArray jsonArray) {
        super(context, R.layout.new_item);
        this.context = context;
        travelData = new ArrayList<Travels.Data>(Travels.IMG_DESCRIPTIONS);
        this.jsonArray = jsonArray;

        this.parentContext = parentContext;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            holder = new CustomViewAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.new_item, null, true);
            final Travels.Data data = travelData.get(position % travelData.size());

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.title_image = (ImageView) convertView.findViewById(R.id.titleimage);



            //System.out.println(position);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobject = null;
                    try {
                        jobject = jsonArray.getJSONObject(position);
                        Intent intent = new Intent(context, NewsDetails.class);
                        intent.putExtra("Data",jobject.toString());
                        intent.putExtra("NewsCategory",parentContext.getTitle());
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            try {
               jObject = jsonArray.getJSONObject(position);
               holder.title.setText(jObject.getString("Title"));
               // Picasso.with(parentContext).load(jObject.getString("imgURL")).into(holder.title_image);

              new ImageDownloader(holder.title_image).execute(jObject.getString("ImageUrl"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            holder = (CustomViewAdapter.ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    @Override
    public int getCount() {

        System.out.println("length: "+jsonArray.length());
        return jsonArray.length();
    }

    private class ViewHolder{
        TextView title;
        ImageView title_image;
    }
}
