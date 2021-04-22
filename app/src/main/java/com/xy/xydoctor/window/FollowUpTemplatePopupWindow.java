package com.xy.xydoctor.window;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FollowUpListAdapter;
import com.xy.xydoctor.bean.FollowListBean;
import com.xy.xydoctor.imp.IAFollowUpChoose;

import java.util.List;

/**
 * 类描述：
 * 类传参：
 *
 * @author android.yys
 * @date 2020/12/18
 */
public class FollowUpTemplatePopupWindow extends PopupWindow {
    private Context context;
    private IAFollowUpChoose iaFollowUpChoose;
    private ImageView imgImageView;
    private ListView followMyListView;
    private TextView sureTextView;
    private List<FollowListBean> list;
    private FollowUpListAdapter adapter;

    public FollowUpTemplatePopupWindow(Context context, List<FollowListBean> list) {
        super(context);
        this.context = context;
        this.list = list;
        View view = View.inflate(context, R.layout.popup_window_follow_list, null);
        imgImageView = view.findViewById(R.id.iv_follow_list_close);
        followMyListView = view.findViewById(R.id.mlv_follow_list);
        sureTextView = view.findViewById(R.id.tv_follow_up_submit);
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
        imgImageView.setOnClickListener(view1 -> {
            dismiss();
        });

        sureTextView.setOnClickListener(v -> {
            dismiss();
            if (!TextUtils.isEmpty(list.get(adapter.getClickPosition()).getId() + "")) {
                iaFollowUpChoose.IAFollowUpChoose(list.get(adapter.getClickPosition()).getId(), list.get(adapter.getClickPosition()).getPlanname());
            }
        });
    }


    private void initValues() {
        adapter = new FollowUpListAdapter(context, list);
        followMyListView.setAdapter(adapter);
        followMyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setClickPosition(position);
            }
        });
    }

    /**
     * 交给activity的方法回调，设置监听
     *
     * @param followUpChoose
     */
    public void setOnChooseOkListener(IAFollowUpChoose followUpChoose) {
        this.iaFollowUpChoose = followUpChoose;
    }


}
