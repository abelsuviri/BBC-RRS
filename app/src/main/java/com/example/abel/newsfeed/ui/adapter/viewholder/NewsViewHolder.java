package com.example.abel.newsfeed.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.abel.newsfeed.R;
import com.example.data.model.Item;
import com.example.viewmodel.util.DateUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Abel Suviri
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail)
    ImageView thumbnail;

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.publishDate)
    TextView publishDate;

    @BindView(R.id.description)
    TextView description;

    private Item news;
    private ViewHolderInterface viewHolderInterface;

    public NewsViewHolder(View itemView, ViewHolderInterface viewHolderInterface) {
        super(itemView);
        this.viewHolderInterface = viewHolderInterface;

        ButterKnife.bind(this, itemView);
    }

    public void bindNews(Item item) {
        news = item;
        title.setText(news.getTitle());
        publishDate.setText(DateUtils.parseDate(news.getPublishDate()));
        description.setText(news.getDescription());
        Picasso.with(thumbnail.getContext()).load(news.getThumbnail().getUrl()).into(thumbnail);
    }

    @OnClick(R.id.itemLayout)
    public void onItemClick() {
        viewHolderInterface.onItemClick(news.getLink());
    }
}