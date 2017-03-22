package com.plusonesoftwares.plusonesoftwares.bignews.unit;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.plusonesoftwares.plusonesoftwares.bignews.CommonClass;
import com.plusonesoftwares.plusonesoftwares.bignews.NewsCategoryDetails;
import com.plusonesoftwares.plusonesoftwares.bignews.R;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Plus 3 on 22-03-2017.
 */

public class NewsCategoryAdapter extends RecyclerView.Adapter<NewsCategoryAdapter.MyViewHolder> {

    Context context;
    List<String> mTextofButton;
    Fragment fragment;
    CommonClass utils;
    RecyclerView recyclerView;

    public NewsCategoryAdapter(Context context, Fragment fragment, RecyclerView recyclerView, List<String> mTextofButton) {
        this.context = context;
        this.mTextofButton = mTextofButton;
        this.fragment = fragment;
        utils = new CommonClass();
        this.recyclerView = recyclerView;
    }

    public NewsCategoryAdapter() {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_cat_card, parent, false);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NewsCategoryDetails.class);
                TextView txt = (TextView)itemView.findViewById(R.id.title);
                intent.putExtra("categoryName", txt.getText().toString());
                Toast.makeText(context,txt.getText().toString(),Toast.LENGTH_LONG).show();
                //Storing current index of flipper
             //   utils.setUserPrefs(utils.flipCurrentIndex, "" ,context);
                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mTextofButton.get(position));
        Glide.with(context).load(R.drawable.cover).into(holder.thumbnail);

        if(fragment instanceof MenuFragment) {
            holder.thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, NewsCategoryDetails.class);
                    intent.putExtra("categoryName", mTextofButton.get(position));
                    //Storing current index of flipper
                    //   utils.setUserPrefs(utils.flipCurrentIndex, "" ,context);
                    context.startActivity(intent);

                }
            });
        }

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, position);
            }
        });
    }

    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        if(fragment instanceof MenuFragment) {
            inflater.inflate(R.menu.menu_options1, popup.getMenu());
        }
        else{
            inflater.inflate(R.menu.menu_option2, popup.getMenu());
        }
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;
        JSONObject JsonCategories;

        public MyMenuItemClickListener(int position) {
            this.position = position;
            JsonCategories = new JSONObject();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.follow:
                    String category = utils.catKeys[position];
                    try {
                       JsonCategories = utils.getUpdatedCategories(context);
                        JsonCategories.put(category, mTextofButton.get(position));
                        utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),context);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                        Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.unfollow:
                    JsonCategories = utils.getUpdatedCategories(context);
                    JsonCategories.remove(utils.getCatIdByCatName(mTextofButton.get(position)));
                    mTextofButton.remove(position);
                    recyclerView.removeViewAt(position);
                    new NewsCategoryAdapter().notifyItemRemoved(position);
                    new NewsCategoryAdapter().notifyItemRangeChanged(position, mTextofButton.size());
                    utils.setUserPrefs(utils.NewsCategories, JsonCategories.toString(),context);
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return mTextofButton.size();
    }
}
