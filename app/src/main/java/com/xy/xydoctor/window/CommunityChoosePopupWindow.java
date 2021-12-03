package com.xy.xydoctor.window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.imp.BaseCallBack;

/**
 * 类描述：
 * 类传参：
 *
 * @author android.yys
 * @date 2020/12/18
 */
public class CommunityChoosePopupWindow extends PopupWindow {
    private Context context;
    private TextView imgImageView;
    private TextView takeTextView;
    private TextView albumTextView;

    public CommunityChoosePopupWindow(Context context, BaseCallBack callBack) {
        super(context);
        this.context = context;
        View view = View.inflate(context, R.layout.popup_window_community_choose, null);
        imgImageView = view.findViewById(R.id.iv_community_choose_close);
        takeTextView = view.findViewById(R.id.tv_community_choose_take);
        albumTextView = view.findViewById(R.id.tv_community_choose_album);
        this.setContentView(view);
        // 设置SelectPicPopupWindow的View
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //        this.setAnimationStyle(R.style.);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.goods_details_transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //因为某些机型是虚拟按键的,所以要加上以下设置防止挡住按键.
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        view.setOnClickListener(v -> {

        });
        imgImageView.setOnClickListener(view1 -> {
            dismiss();
            callBack.callBack(0);
        });

        takeTextView.setOnClickListener(v -> {
            dismiss();
            callBack.callBack(1);


        });
        albumTextView.setOnClickListener(v -> {
            dismiss();
            callBack.callBack(2);

        });
    }

}
