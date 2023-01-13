package com.thinkbold.abenry.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.thinkbold.abenry.News;
import com.thinkbold.abenry.NewsDetails;
import com.thinkbold.abenry.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private RecyclerViewClickListener listener;

    private Context ctx;
    private ArrayList<NewsModel> newsList;

    //private List<NewsModel> newsList;
    public NewsAdapter(News ctx, ArrayList<NewsModel> newsList) {
        this.ctx = ctx;
        this.newsList = newsList;
    }


    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.listwiewdesign, null);
        return new NewsViewHolder(view);
    }

    public void onBindViewHolder(NewsViewHolder holder, int position) {

        NewsModel newsModel = newsList.get(position);

        //loading the image

        Picasso.get().load(newsModel.getImage()).into(holder.imageView);
        holder.textViewTitle.setText(newsModel.getTitle());
        holder.textViewTdesc.setText(newsModel.getDesc());
        holder.textViewDate.setText(newsModel.getDate());
        holder.category.setText(newsModel.getCategory());
        holder.relativeNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, NewsDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("getTitle", newsModel.getTitle());
                bundle.putString("getDate", newsModel.getDate());
                bundle.putString("getImage", newsModel.getImage());
                bundle.putString("getDesc", newsModel.getDesc());
                bundle.putString("getCat", newsModel.getCategory());
                intent.putExtras(bundle);
                ctx.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return newsList.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewTitle, textViewDate,textViewTdesc, category;
        ImageView imageView;
        RelativeLayout relativeNews;

        public NewsViewHolder(@NonNull View view) {
            super(view);
            textViewTitle = view.findViewById(R.id.textViewTitle);
            textViewDate = view.findViewById(R.id.textViewpostdDate);
            textViewTdesc = view.findViewById(R.id.textViewTdesc);
            imageView = view.findViewById(R.id.imageNews);
            relativeNews = view.findViewById(R.id.relativeNews);
            category = view.findViewById(R.id.textViewCategory);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());

        }
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }


}
