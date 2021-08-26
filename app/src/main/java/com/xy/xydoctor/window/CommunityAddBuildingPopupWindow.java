package com.xy.xydoctor.window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.community_manamer.AddBuildingInfo;
import com.xy.xydoctor.utils.toChineseNumUtill;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 15:02
 * Description: 添加楼栋
 */
public class CommunityAddBuildingPopupWindow extends PopupWindow {
    private LinearLayout addLiner;
    private TextView saveTextView;
    private int size;

    private Context context;

    private List<AddBuildingInfo> list = new ArrayList<>();

    public CommunityAddBuildingPopupWindow(Context context, int size) {
        super(context);
        this.context = context;
        this.size = size;

        View view = View.inflate(context, R.layout.popupwindow_add_building, null);
        addLiner = view.findViewById(R.id.ll_add_unit);
        addView();
        this.setContentView(view);
        initValues();

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


    }

    private void initValues() {

    }


    private void addView() {
        addLiner.removeAllViews();
        for (int i = 1; i <= size; i++) {
            View contentView = View.inflate(context, R.layout.include_add_building, null);
            EditText unitEditText = contentView.findViewById(R.id.et_add_building_unit_pop);
            TextView numTextView = contentView.findViewById(R.id.tv_add_building_num_pop);
            numTextView.setText(toChineseNumUtill.TooltoCh(i));
            unitEditText.setTag(i);
            int finalI = i;
            unitEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    list.get(finalI).setUnit(s.toString());
                }
            });
            addLiner.addView(contentView);
        }

    }

}
