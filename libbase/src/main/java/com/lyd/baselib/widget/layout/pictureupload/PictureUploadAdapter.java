package com.lyd.baselib.widget.layout.pictureupload;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lyd.baselib.R;

import java.util.List;

/**
 * 描述：
 * @author zhangqin
 * @date 2018/5/31
 */
public class PictureUploadAdapter<T extends PictureUploadModel> extends BaseQuickAdapter<T, BaseViewHolder> {

    public PictureUploadAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        helper.addOnClickListener(R.id.img_pic).addOnClickListener(R.id.img_del);
        ImageView imgPic = helper.getView(R.id.img_pic);
        if (item != null) {
            Glide.with(Utils.getApp()).load(item.getPictureImage()).error(R.drawable.picture_upload_error).placeholder(R.drawable.picture_upload_error).into(imgPic);
            helper.setVisible(R.id.img_del, true);
        } else {
            imgPic.setImageResource(R.drawable.picture_upload_add);
            helper.setVisible(R.id.img_del, false);
        }
    }
}
