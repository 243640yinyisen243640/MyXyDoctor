package com.xy.xydoctor.ui.activity.groupmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.GroupManageFragmentAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.ui.fragment.GroupAddFragment;
import com.xy.xydoctor.ui.fragment.GroupListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 描述: 分组管理首页
 * 作者: LYD
 * 创建日期: 2019/3/1 10:33
 */
public class GroupManageActivity extends BaseActivity {
    @BindView(R.id.tl_tab)
    TabLayout tlTab;
    @BindView(R.id.vp_content)
    ViewPager2 vpContent;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_manage;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("分组管理");
        setViewPager2();
    }

    private void setViewPager2() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new GroupListFragment());
        fragmentList.add(new GroupAddFragment());
        GroupManageFragmentAdapter adapter = new GroupManageFragmentAdapter(this, fragmentList);
        vpContent.setAdapter(adapter);

        //TabLayout和ViewPager的绑定
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tlTab, vpContent, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                View view = View.inflate(getPageContext(), R.layout.tab_item_group_manage, null);
                TextView tvTitle = view.findViewById(R.id.tv_title);
                ImageView imgTitle = view.findViewById(R.id.img_title);
                ImageView imgRed = view.findViewById(R.id.img_red_triangle);
                switch (position) {
                    case 0:
                        tvTitle.setText("分组列表");
                        tvTitle.setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
                        imgTitle.setImageDrawable(getPageContext().getResources().getDrawable(R.drawable.group_manage_left_selector));
                        imgRed.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        tvTitle.setText("新建分组");
                        tvTitle.setTextColor(ContextCompat.getColor(getPageContext(), R.color.gray_text));
                        imgTitle.setImageDrawable(getPageContext().getResources().getDrawable(R.drawable.group_manage_right_selector));
                        imgRed.setVisibility(View.INVISIBLE);
                        break;
                }
                tab.setCustomView(view);
            }
        });
        tabLayoutMediator.attach();


        tlTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                assert tab.getCustomView() != null;
                View customView = tab.getCustomView();
                TextView tvTitle = customView.findViewById(R.id.tv_title);
                tvTitle.setSelected(true);
                tvTitle.setTextColor(ContextCompat.getColor(getPageContext(), R.color.main_red));
                customView.findViewById(R.id.img_title).setSelected(true);
                customView.findViewById(R.id.img_red_triangle).setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                assert tab.getCustomView() != null;
                View customView = tab.getCustomView();
                TextView tvTitle = customView.findViewById(R.id.tv_title);
                tvTitle.setSelected(false);
                tvTitle.setTextColor(ContextCompat.getColor(getPageContext(), R.color.gray_text));
                customView.findViewById(R.id.img_title).setSelected(false);
                customView.findViewById(R.id.img_red_triangle).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
