package com.giologic.pedrosystem10;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.giologic.pedrosystem10.model.Post;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by JKC on 14/03/2016.
 */
public abstract class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
    private ArrayList<Post> items = new ArrayList<Post>();

    public class HomeViewHolder extends RecyclerView.ViewHolder{

        TextView tvOrgName;
        ScaleImageView ivLogo;
        TextView tvMessage;
        TextView tvDate;
        ScaleImageView ivPub;
        View v;
        public HomeViewHolder(View itemView) {
            super(itemView);
            tvOrgName = (TextView) itemView.findViewById(R.id.tv_org_name);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
            ivLogo = (ScaleImageView) itemView.findViewById(R.id.iv_org_logo);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            ivPub = (ScaleImageView) itemView.findViewById(R.id.iv_pub);
            v = itemView;
        }
    }

    public void removeAll() {
        items.removeAll(items);
    }

    public void add(Post object) {
        items.add(object);
        notifyDataSetChanged();
    }

    public void add(int index, Post object) {
        items.add(index, object);
        notifyDataSetChanged();
    }

    public void addAll(Collection<Post> collection) {
        if (collection != null) {
            items.addAll(collection);
            notifyDataSetChanged();
        }
    }

    public void addAll(String... items) {
        addAll(items);
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void remove(String object) {
        items.remove(object);
        notifyDataSetChanged();
    }

    public Post getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
