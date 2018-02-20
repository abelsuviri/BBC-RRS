package com.example.abel.newsfeed.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.abel.newsfeed.R;
import com.example.abel.newsfeed.ui.adapter.viewholder.NewsViewHolder;
import com.example.abel.newsfeed.ui.adapter.viewholder.ViewHolderInterface;
import com.example.data.model.Item;

import java.util.List;

/**
 * @author Abel Suviri
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private List<Item> newsList;

    private ViewHolderInterface viewHolderInterface;

    public NewsAdapter(List<Item> newsList, ViewHolderInterface viewHolderInterface) {
        this.newsList = newsList;
        this.viewHolderInterface = viewHolderInterface;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(itemView, viewHolderInterface);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder viewHolder, int position) {
        Item item = newsList.get(position);
        viewHolder.bindNews(item);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}