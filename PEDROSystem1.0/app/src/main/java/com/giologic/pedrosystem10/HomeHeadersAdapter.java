package com.giologic.pedrosystem10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.giologic.pedrosystem10.model.Post;
import com.j256.ormlite.stmt.query.Not;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;

/**
 * Created by JKC on 14/03/2016.
 */
    public class HomeHeadersAdapter extends HomeAdapter
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {


    @Override
    public long getHeaderId(int position) {
        return getItem(position).getOrg().getId();
    }




    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };

    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

//        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.img);
        TextView textView = (TextView) holder.itemView.findViewById(R.id.tv_home_header);
        if(getItem(position).getOrg().getName() != null)textView.setText(getItem(position).getOrg().getName());
        if(getItem(position).getOrg().getColor() != null )textView.setBackgroundColor(Color.parseColor("#" + getItem(position).getOrg().getColor()));;



        //textView.getBackground().setAlpha(100);
// textView.setTextColor(Color.parseColor("#02713D"));
//        textView.setBackgroundColor(Color.WHITE);
    }

    private int getColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
        return 0;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_home, parent, false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        holder.tvOrgName.setText(getItem(position).getOrg().getName());
        holder.tvMessage.setText(getItem(position).getContents());
        holder.tvDate.setText(getItem(position).getDate().toString());
        holder.ivLogo.setImageDrawable(getItem(position).getDrawableLogo());
        holder.ivPub.setImageDrawable(getItem(position).getDrawablePub());
    }
}