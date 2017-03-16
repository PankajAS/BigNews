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

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Plus 3 on 07-03-2017.
 */

public class CustomViewAdapter extends ArrayAdapter {

    Context context;
    JSONArray jsonArray;
    CustomViewAdapter.ViewHolder holder = null;
    JSONObject jObject;
    Activity parentContext;
    JSONArray newsitems;
    String title;

    public CustomViewAdapter(Context context, Activity parentContext, JSONArray jsonArray) {
        super(context, R.layout.new_item);
        this.context = context;
        this.jsonArray = jsonArray;
        this.parentContext = parentContext;
    }

    public CustomViewAdapter(Context context, JSONArray jsonArray, String title) {
        super(context, R.layout.new_item);
        this.context = context;
        this.jsonArray = jsonArray;
        this.title = title;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.new_item, null, true);
            holder = new CustomViewAdapter.ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.title_image = (ImageView) convertView.findViewById(R.id.titleimage);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobject = null;
                    try {
                        jobject = jsonArray.getJSONObject(position);
                        Intent intent = new Intent(context, NewsDetails.class);
                        intent.putExtra("Data", jobject.toString());
                        if (parentContext != null)
                            intent.putExtra("NewsCategory", parentContext.getTitle());
                        else
                            intent.putExtra("NewsCategory", title);
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        try {
            jObject = jsonArray.getJSONObject(position);
            holder.title.setText(jObject.getString("Title"));
            Picasso.with(parentContext)
                    .load("http:" + jObject.getString("ImageUrl"))
                    .into(holder.title_image);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    @Override
    public int getCount() {
        if (jsonArray != null) {
            return jsonArray.length();
        } else if (newsitems != null) {
            return newsitems.length();
        }
        return 0;
    }

    private class ViewHolder {
        TextView title;
        ImageView title_image;
    }
}
