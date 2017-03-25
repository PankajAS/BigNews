package com.plusonesoftwares.plusonesoftwares.bignews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    RelativeLayout layoutSingleRow;
    int headerFooterMargin = 0;
    CommonClass clsComman;

    public CustomViewAdapter(Context context, Activity parentContext, JSONArray jsonArray) {
        super(context, R.layout.new_item);
        this.context = context;
        this.jsonArray = jsonArray;
        this.parentContext = parentContext;
        clsComman = new CommonClass();
    }

    public CustomViewAdapter(Context context, JSONArray jsonArray, String title) {
        super(context, R.layout.new_item);
        this.context = context;
        this.jsonArray = jsonArray;
        this.title = title;
        clsComman = new CommonClass();
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

            //******* setting list row height to fit all 4 rows on screen**********
            if (parentContext != null)
                headerFooterMargin = getNavBarHeight(parentContext, true);
            else {
                headerFooterMargin = getNavBarHeight((Activity) context, false);
            }


            layoutSingleRow = (RelativeLayout) convertView.findViewById(R.id.layoutSingleRow);
            //System.out.print("Row Height: " + (Resources.getSystem().getDisplayMetrics().heightPixels));
            RelativeLayout.LayoutParams rel_params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ((Resources.getSystem().getDisplayMetrics().heightPixels) - headerFooterMargin) / 4);
            layoutSingleRow.setLayoutParams(rel_params);
            //******* setting list row height to fit 4 rows on screen**********

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject jobject = null;
                    try {
                        jobject = jsonArray.getJSONObject(position);
                        Intent intent = new Intent(context, NewsDetails.class);
                        intent.putExtra("Data", jobject.toString());
                        intent.putExtra("SourceLink", jobject.getString("SourceLink"));
                        //Storing current index of flipper
                        clsComman.setUserPrefs(clsComman.flipCurrentIndex, "", context);
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

            //System.out.println("Category: " + jObject.getString("Category"));

            //System.out.println("UniqueID " + jObject.getString("UniqueID"));

            if (clsComman.haveNetworkConnection(context)) {
                Picasso.with(parentContext)
                        .load("http:" + jObject.getString("ImageUrl"))
                        .into(holder.title_image);
            } else {

                String base64Image = (String) jObject.getString("ImageByteArray");
                if (base64Image != null && !base64Image.isEmpty()) {
                    byte[] imageAsBytes = Base64.decode(base64Image.getBytes(), Base64.DEFAULT);
                    Bitmap image = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    holder.title_image.setImageBitmap(image);
                }
            }

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

    public int getNavBarHeight(Activity parentContext, Boolean isFooter) {
        Rect rectangle = new Rect();
        Window window = parentContext.getWindow();
        ;
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        ;
        //int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        Resources resources = parentContext.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");

        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId) * (isFooter ? 2 : 1) + statusBarHeight;
        }
        return 0;
    }
}

