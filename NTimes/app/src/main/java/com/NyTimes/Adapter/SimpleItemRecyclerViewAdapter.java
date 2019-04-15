package com.NyTimes.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.NyTimes.MainActivity;
import com.NyTimes.Model.MostPopularModel;
import com.NyTimes.Model.Result;
import com.NyTimes.NewsDetailActivity;
import com.NyTimes.R;
import com.NyTimes.Settings;
import com.bumptech.glide.Glide;

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.MyViewHolder> {

    private final MainActivity mParentActivity;
    private final MostPopularModel mValues;

    public SimpleItemRecyclerViewAdapter(MainActivity parent,
                                         MostPopularModel items) {
        mValues = items;
        mParentActivity = parent;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ContentImageView;
        public TextView idTextView, contentTitleTextView, byTextView, dateTextView;

        public MyViewHolder(View view) {
            super(view);
            ContentImageView = (ImageView) view.findViewById(R.id.ContentImageView);
            idTextView = (TextView) view.findViewById(R.id.idTextView);
            contentTitleTextView = (TextView) view.findViewById(R.id.contentTitleTextView);
            byTextView = (TextView) view.findViewById(R.id.byTextView);
            dateTextView = (TextView) view.findViewById(R.id.dateTextView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.idTextView.setText((position + 1) + "");
        holder.contentTitleTextView.setText(mValues.getResults().get(position).getTitle());
        holder.byTextView.setText(mValues.getResults().get(position).getByline());
        holder.dateTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.ic_date_range_black_24dp,0,0,0);
        holder.dateTextView.setGravity(Gravity.CENTER_VERTICAL);
        holder.dateTextView.setText(mValues.getResults().get(position).getPublishedDate());
        Glide.with(mParentActivity).load(mValues.getResults().
                get(position).getMedia().get(0).getMediaMetadata().get(2).getUrl())
                .into(holder.ContentImageView);
        holder.itemView.setTag(mValues.getResults().get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Result item = (Result) view.getTag();
                Settings.URLVALUE = item.getUrl();
                Log.e("VALUES","VALUES>>>"+item.getUrl());
                    Context context = view.getContext();
                    Intent intent = new Intent(context, NewsDetailActivity.class);
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.getResults().size();
    }

}
