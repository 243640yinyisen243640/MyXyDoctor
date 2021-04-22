package com.xy.xydoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.xy.xydoctor.R;
import com.xy.xydoctor.utils.engine.GlideImageEngine;

import java.util.ArrayList;
import java.util.List;

public class AddPicAdapter extends RecyclerView.Adapter<AddPicAdapter.ViewHolder> {
    private int selectMax = 3;
    private List<String> list;
    private Context context;

    public AddPicAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return selectMax;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_examine, viewGroup, false);
        return new ViewHolder(view);
    }


    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (position >= list.size()) {
            viewHolder.ivExamine.setImageResource(R.drawable.jiahao_check);
        } else {
            Glide.with(Utils.getApp()).load(list.get(position)).into(viewHolder.ivExamine);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MNImageBrowser.with(context)
                            .setCurrentPosition(position)
                            .setImageEngine(new GlideImageEngine())
                            .setImageList((ArrayList<String>) list)
                            .setIndicatorHide(false)
                            .setFullScreenMode(true)
                            .show(viewHolder.ivExamine);
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExamine;

        ViewHolder(View view) {
            super(view);
            ivExamine = view.findViewById(R.id.iv_examine);
        }
    }
}
