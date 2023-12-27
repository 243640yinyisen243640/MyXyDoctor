package com.xy.xydoctor.ui.activity.patienteducation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.rxjava.rxlife.RxLife;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.PatientEducationArticleAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.PatientEducationArticleListBean;
import com.xy.xydoctor.imp.OnItemClickListener;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;


/**
 * 描述: 患教文章列表
 * 作者: LYD
 * 创建日期: 2019/6/1 15:50
 */
public class PatientEducationArticleListActivity extends BaseActivity {
    private static final String TAG = "PatientEducationArticleListActivity";

    @BindView(R.id.rv_article)
    RecyclerView rvArticle;
    @BindView(R.id.srl_article)
    SmartRefreshLayout srlArticle;

    @BindView(R.id.tv_submit)
    TextView tvSubmit;


    private String type = "";


    private int pageIndex = 1;//当前获取的是第几页的数据
    private List<PatientEducationArticleListBean.DataBean> list = new ArrayList<>();//ListView显示的数据
    private List<PatientEducationArticleListBean.DataBean> tempList;//用于临时保存ListView显示的数据
    private PatientEducationArticleAdapter adapter;

    private int selectPosition = -1;


    private void initRefresh() {
        srlArticle.setEnableAutoLoadMore(true);//开启自动加载功能(非必须）
        //下拉刷新
        srlArticle.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getArticleList();
                srlArticle.finishRefresh();
                refreshLayout.setNoMoreData(false);//恢复没有更多数据的原始状态
            }
        });
        //上拉加载
        srlArticle.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                ++pageIndex;
                getArticleList();
            }
        });
    }

    private void getArticleList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("page", pageIndex);
        RxHttp.postForm(XyUrl.GET_EDU_ARTICLE_LIST)
                .addAll(map)
                .asResponse(PatientEducationArticleListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<PatientEducationArticleListBean>() {
                    @Override
                    public void accept(PatientEducationArticleListBean data) throws Exception {
                        tempList = data.getData();
                        //少于10条,将不会再次触发加载更多事件
                        if (tempList.size() < 10) {
                            srlArticle.finishLoadMoreWithNoMoreData();
                        } else {
                            srlArticle.finishLoadMore();
                        }
                        //设置数据
                        if (pageIndex == 1) {
                            if (list == null) {
                                list = new ArrayList<>();
                            } else {
                                list.clear();
                            }
                            list.addAll(tempList);
                            adapter = new PatientEducationArticleAdapter(list);
                            rvArticle.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvArticle.setAdapter(adapter);
                            adapter.setOnItemClickListener(new OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    adapter.setSelection(position);
                                    selectPosition = position;
                                }
                            });
                        } else {
                            list.addAll(tempList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    private void initTitle() {
        setTitle("患教文章");
    }

    @OnClick({R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (-1 == selectPosition) {
                    ToastUtils.showShort("请先选择患教文章");
                    return;
                }
                if ("1".equals(type)) {
                    Intent intent = new Intent();
                    intent.putExtra("articleList", (Serializable) list);
                    intent.putExtra("selectPosition", selectPosition);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("articleList", list.get(selectPosition));
                    intent.putExtra("selectPosition", list.get(selectPosition).getId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;

            default:
                break;
        }

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_education_article_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getStringExtra("type");
        initTitle();
        getArticleList();
        initRefresh();
    }
}
