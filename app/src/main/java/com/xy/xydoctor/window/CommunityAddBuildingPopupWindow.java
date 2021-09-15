package com.xy.xydoctor.window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
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
import com.xy.xydoctor.bean.community_manamer.UpLoadParamAddBuildingInfo;
import com.xy.xydoctor.imp.IACommunityUpLoadChoose;
import com.xy.xydoctor.utils.NumberToChineseUtil;
import com.xy.xydoctor.utils.TipUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: LYD
 * Date: 2021/8/26 15:02
 * Description: 添加楼栋
 */
public class CommunityAddBuildingPopupWindow extends PopupWindow {
    private LinearLayout addLiner;
    private int size;

    private Context context;

    private IACommunityUpLoadChoose iaCommunityFilterChoose;

    private List<UpLoadParamAddBuildingInfo> list;

    public CommunityAddBuildingPopupWindow(Context context, int size) {
        super(context);
        this.context = context;
        this.size = size;

        View view = View.inflate(context, R.layout.popupwindow_add_building, null);
        addLiner = view.findViewById(R.id.ll_add_unit);
        TextView saveTextView = view.findViewById(R.id.tv_add_building_save);
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new UpLoadParamAddBuildingInfo());
        }
        addView();
        this.setContentView(view);

        // 设置SelectPicPopupWindow的View
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(FrameLayout.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(FrameLayout.LayoutParams.MATCH_PARENT);
        //        this.setHeight(XyScreenUtils.dip2px(context, 300));
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.Base_Window_Fade_Anim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.goods_details_transparent));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //因为某些机型是虚拟按键的,所以要加上以下设置防止挡住按键.
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        view.setOnClickListener(v -> {

        });

        saveTextView.setOnClickListener(v -> {
            for (int i = 0; i < list.size(); i++) {
                if (TextUtils.isEmpty(list.get(i).getHousehold())){
                    TipUtils.getInstance().showToast(context, R.string.plwase_input_every_unit);
                    return;
                }
                dismiss();
            }
            iaCommunityFilterChoose.IAUpParamChoose(list);
        });
    }


    private void addView() {
        addLiner.removeAllViews();
        for (int i = 0; i < size; i++) {//
            View contentView = View.inflate(context, R.layout.include_add_building, null);
            EditText unitEditText = contentView.findViewById(R.id.et_add_building_unit_pop);
            TextView numTextView = contentView.findViewById(R.id.tv_add_building_num_pop);
            String unit_name = NumberToChineseUtil.intToChinese(i) + "单元";
            numTextView.setText(unit_name);
            addLiner.addView(contentView);
            list.get(i).setUnit_name(unit_name);
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
                    list.get(finalI).setHousehold(s.toString());
                }
            });
        }

    }

    public void setOnChooseOkListener(IACommunityUpLoadChoose filterChoose) {
        this.iaCommunityFilterChoose = filterChoose;
    }

}
