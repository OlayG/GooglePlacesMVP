package com.example.admin.googleplacesmvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.googleplacesmvp.R;
import com.example.admin.googleplacesmvp.model.NearbyPlacesModel;
import com.example.admin.googleplacesmvp.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/26/2017.
 */

public class GooglePlacesRecyclerView extends RecyclerView.Adapter<GooglePlacesRecyclerView.ViewHolder> {

    List<Result> resultsList = new ArrayList<>();
    Context context;

    public GooglePlacesRecyclerView(List<Result> results, Context context) {
        this.resultsList = results;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Result result = resultsList.get(position);

        holder.tvName.setText(result.getName());
        holder.tvCatAndDis.setText(result.getTypes().get(1));
        holder.tvRating.setText(result.getVicinity());

        Glide.with(context)
        .load(result.getIcon())
        .placeholder(android.R.drawable.gallery_thumb)
        .fitCenter()
        .into(holder.ivStorePic);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCatAndDis, tvRating;
        ImageView ivStorePic;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCatAndDis = (TextView) itemView.findViewById(R.id.tvCatAndDis);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            ivStorePic = (ImageView) itemView.findViewById(R.id.ivStorePic);

        }
    }
}
