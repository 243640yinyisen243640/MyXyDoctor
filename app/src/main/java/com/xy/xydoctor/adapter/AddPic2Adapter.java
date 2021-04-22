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

public class AddPic2Adapter extends RecyclerView.Adapter<AddPic2Adapter.ViewHolder> {
    private static final int TYPE_CAMERA = 1;
    private static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private List<String> list = new ArrayList<>();
    private int selectMax = 3;
    private Context context;
    private String status;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;
    private OnItemClickListener mItemClickListener;

    public AddPic2Adapter(Context context, onAddPicClickListener mOnAddPicClickListener) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    @Override
    public int getItemCount() {
        return selectMax;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public void setList(List<String> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_photo_examine, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }
    /**
     * 创建ViewHolder
     */


    private boolean isShowAddItem(int position) {
        return position >= list.size();
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.ivExamine.setImageResource(R.drawable.jiahao_check);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!"5".equals(status)) {
                        mOnAddPicClickListener.onAddPicClick(viewHolder.getAdapterPosition());
                    }
                }
            });
            //viewHolder.tvDel.setVisibility(View.INVISIBLE);
        } else {
            //viewHolder.tvDel.setVisibility(View.VISIBLE);
            viewHolder.ivExamine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("5".equals(status)) {
                        MNImageBrowser.with(context)
                                .setCurrentPosition(position)
                                .setImageEngine(new GlideImageEngine())
                                .setImageList((ArrayList<String>) list)
                                .setIndicatorHide(false)
                                .setFullScreenMode(true)
                                .show(viewHolder.ivExamine);
                    } else {
                        mOnAddPicClickListener.onAddPicClick(viewHolder.getAdapterPosition());
                    }
                }
            });


            Glide.with(Utils.getApp()).load(list.get(position)).placeholder(R.drawable.default_image).into(viewHolder.ivExamine);

            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivExamine;

        ViewHolder(View view) {
            super(view);
            ivExamine = view.findViewById(R.id.iv_examine);
        }
    }
    public interface onAddPicClickListener {
        void onAddPicClick(int position);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }
}
