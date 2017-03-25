package com.plusonesoftwares.plusonesoftwares.bignews;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.DiscoverFragment;
import com.plusonesoftwares.plusonesoftwares.bignews.TabFragments.MenuFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    JSONObject JsonCategoriesfollowed;
    JSONObject JsonCategoriesUnfollowed;

    public NewsCategoryAdapter(Context context, Fragment fragment, RecyclerView recyclerView, List<String> mTextofButton) {
        this.context = context;
        this.mTextofButton = mTextofButton;
        this.fragment = fragment;
        utils = new CommonClass();
        this.recyclerView = recyclerView;
        JsonCategoriesfollowed = new JSONObject();
        JsonCategoriesUnfollowed = new JSONObject();
    }

    public NewsCategoryAdapter() {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;
        public CheckBox chkSelection;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            //count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            chkSelection = (CheckBox) view.findViewById(R.id.selection);
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
                TextView txt = (TextView) itemView.findViewById(R.id.title);
                intent.putExtra("categoryName", txt.getText().toString());
                // Toast.makeText(context,txt.getText().toString(),Toast.LENGTH_LONG).show();
                //Storing current index of flipper
                //   utils.setUserPrefs(utils.flipCurrentIndex, "" ,context);
                context.startActivity(intent);
            }
        });
        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.title.setText(mTextofButton.get(position));
        Glide.with(context).load(R.drawable.cover).into(holder.thumbnail);
        holder.chkSelection.setVisibility(View.GONE);

        if (fragment instanceof DiscoverFragment) {
            holder.overflow.setVisibility(View.GONE);
            holder.chkSelection.setVisibility(View.VISIBLE);
            holder.chkSelection.setTag(mTextofButton.get(position));

        }

        holder.chkSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.chkSelection.isChecked()) {
                    //Toast.makeText(context,holder.chkSelection.getTag().toString(),Toast.LENGTH_SHORT).show();
                    holder.chkSelection.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                    JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                    try {

                        JsonCategoriesfollowed.put(utils.getCatIdByCatName(holder.chkSelection.getTag().toString()), holder.chkSelection.getTag().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // mTextofButton.remove(position);
                    utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                } else if (!holder.chkSelection.isChecked()) {
                    JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                    holder.chkSelection.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                    JsonCategoriesfollowed.remove(utils.getCatIdByCatName(holder.chkSelection.getTag().toString()));
                    utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                }
            }
        });


        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            Boolean check = false;

            @Override
            public void onClick(View view) {
                if (fragment instanceof MenuFragment) {
                    Intent intent = new Intent(context, NewsCategoryDetails.class);
                    intent.putExtra("categoryName", mTextofButton.get(position));
                    //Storing current index of flipper
                    //utils.setUserPrefs(utils.flipCurrentIndex, "" ,context);
                    context.startActivity(intent);
                } else {
                    check = !check;
                    holder.chkSelection.setChecked(check);

                    if (holder.chkSelection.isChecked()) {
                        //Toast.makeText(context,holder.chkSelection.getTag().toString(),Toast.LENGTH_SHORT).show();
                        holder.chkSelection.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
                        JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                        try {

                            JsonCategoriesfollowed.put(utils.getCatIdByCatName(holder.chkSelection.getTag().toString()), holder.chkSelection.getTag().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // mTextofButton.remove(position);
                        utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                    } else if (!holder.chkSelection.isChecked()) {
                        JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                        holder.chkSelection.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                        JsonCategoriesfollowed.remove(utils.getCatIdByCatName(holder.chkSelection.getTag().toString()));
                        utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                    }

                     /* String category = mTextofButton.get(position);
                        JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                        try {
                            JsonCategoriesfollowed.put(utils.getCatIdByCatName(category),category);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonCategoriesfollowed.remove(utils.getCatIdByCatName(mTextofButton.get(position)));
                        mTextofButton.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, mTextofButton.size());
                        utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(),context);
                        //recyclerView.removeViewAt(position);
                        notifyDataSetChanged();*/
                }
            }
        });


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
        if (fragment instanceof MenuFragment) {
            inflater.inflate(R.menu.menu_options1, popup.getMenu());
        }
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
            JsonCategoriesfollowed = new JSONObject();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case R.id.follow:
                    String category = utils.catKeys[position];
                    try {
                        JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                        JsonCategoriesfollowed.put(category, mTextofButton.get(position));
                        utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.unfollow:
                    JsonCategoriesfollowed = utils.getUpdatedCategories(context);
                    JsonCategoriesfollowed.remove(utils.getCatIdByCatName(mTextofButton.get(position)));
                    mTextofButton.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    utils.setUserPrefs(utils.NewsCategories, JsonCategoriesfollowed.toString(), context);
                    // recyclerView.removeViewAt(position);
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
