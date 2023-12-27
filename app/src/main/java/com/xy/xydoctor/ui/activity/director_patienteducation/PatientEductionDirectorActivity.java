package com.xy.xydoctor.ui.activity.director_patienteducation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lyd.baselib.util.eventbus.BindEventBus;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.lyd.baselib.widget.view.MyListView;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgDirectorDoctorListAdapter;
import com.xy.xydoctor.adapter.PatientEducationArticleLvAdapter;
import com.xy.xydoctor.adapter.SelectUserAdapter;
import com.xy.xydoctor.base.activity.BaseEventBusActivity;
import com.xy.xydoctor.bean.DoctorListBean;
import com.xy.xydoctor.bean.PatientEducationArticleListBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.director_massmsg.SelectPeopleListDirectorActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgHaveSelectPeopleActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgHistoryActivity;
import com.xy.xydoctor.ui.activity.patienteducation.PatientEducationArticleListActivity;
import com.xy.xydoctor.ui.activity.patienteducation.PatientEducationHistoryListActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:
 * 作者: LYD
 * 创建日期: 2020/5/19 17:59
 */
@BindEventBus
public class PatientEductionDirectorActivity extends BaseEventBusActivity {
    private static final int SELECT = 10010;
    private static final int HAVE_SELECT = 10011;
    private static final int SELECT_ARTICLE = 10086;
    @BindView(R.id.rv_doctor_list)
    RecyclerView rvDoctorList;
    @BindView(R.id.img_right_arrow)
    ImageView imgRightArrow;
    @BindView(R.id.rl_have_select)
    RelativeLayout rlHaveSelect;
    @BindView(R.id.rv_have_select_list)
    RecyclerView rvHaveSelectList;
    @BindView(R.id.img_right_arrow_article)
    ImageView imgRightArrowArticle;
    @BindView(R.id.rl_patient_education_article)
    RelativeLayout rlPatientEducationArticle;
    @BindView(R.id.img_right_arrow_article_have_select)
    ImageView imgRightArrowArticleHaveSelect;
    @BindView(R.id.rl_patient_education_article_have_select)
    RelativeLayout rlPatientEducationArticleHaveSelect;
    @BindView(R.id.lv_article_list)
    MyListView lvArticleList;
    @BindView(R.id.el_article)
    ExpandableLayout elArticle;
    @BindView(R.id.bt_send)
    Button btSend;


    private ArrayList<GroupUserBean> mainList;
    private SelectUserAdapter selectAdapter;
    private ArrayList<PatientEducationArticleListBean.DataBean> articleSelectList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_eduction_director;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("患者宣教");
        getDoctorList();
        initRv();
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent massMsgIntent = new Intent(getPageContext(), MassMsgHistoryActivity.class);
                massMsgIntent.putExtra("type", getIntent().getStringExtra("type"));
                startActivity(massMsgIntent);
            }
        });
    }


    private void initRv() {
        mainList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置滑动方向：横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHaveSelectList.setLayoutManager(layoutManager);
        selectAdapter = new SelectUserAdapter(mainList);
        rvHaveSelectList.setAdapter(selectAdapter);
        rvHaveSelectList.setVisibility(View.GONE);
    }

    private void getDoctorList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_DOCTOR_LIST)
                .addAll(map)
                .asResponseList(DoctorListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<DoctorListBean>>() {
                    @Override
                    public void accept(List<DoctorListBean> list) throws Exception {
                        if (list != null && list.size() > 0) {
                            MassMsgDirectorDoctorListAdapter adapter = new MassMsgDirectorDoctorListAdapter(list);
                            rvDoctorList.setLayoutManager(new LinearLayoutManager(getPageContext()));
                            rvDoctorList.setAdapter(adapter);
                            adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                                    switch (view.getId()) {
                                        case R.id.img_check:
                                            if (!adapter.isSelected.get(position)) {
                                                //修改map的值保存状态
                                                adapter.isSelected.put(position, true);
                                                adapter.notifyItemChanged(position);
                                                //selectDatasSecond.add(position + 1 + "");
                                                //添加
                                                List<GroupUserBean> userids = list.get(position).getUserids();
                                                mainList.addAll(userids);
                                                rvHaveSelectList.setVisibility(View.VISIBLE);
                                                selectAdapter.notifyDataSetChanged();
                                            } else {
                                                //修改map的值保存状态
                                                adapter.isSelected.put(position, false);
                                                adapter.notifyItemChanged(position);
                                                //selectDatasSecond.remove(position + 1 + "");
                                                //删除
                                                List<GroupUserBean> userids = list.get(position).getUserids();
                                                mainList.removeAll(userids);
                                                selectAdapter.notifyDataSetChanged();
                                            }
                                            break;
                                        case R.id.tv_doctor_name:
                                            List<GroupUserBean> userids = list.get(position).getUserids();
                                            Intent intent = new Intent(getPageContext(), SelectPeopleListDirectorActivity.class);
                                            intent.putExtra("userids", (Serializable) userids);
                                            intent.putExtra("checkList", mainList);
                                            intent.putExtra("type", "patientEducation");
                                            startActivity(intent);
                                            break;
                                    }
                                }
                            });
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    @OnClick({
            R.id.rl_have_select,
            R.id.rl_patient_education_article,
            R.id.rl_patient_education_article_have_select,
            R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_have_select:
                if (mainList != null && mainList.size() > 0) {
                    Intent intent = new Intent(getPageContext(), MassMsgHaveSelectPeopleActivity.class);
                    List<GroupUserBean> list = new ArrayList(mainList);
                    intent.putExtra("haveSelectPeople", (Serializable) list);
                    startActivityForResult(intent, HAVE_SELECT);
                } else {
                    ToastUtils.showShort("还没有选择消息接收人");
                }
                break;
            case R.id.rl_patient_education_article:
                Intent intent = new Intent(getPageContext(), PatientEducationArticleListActivity.class);
                intent.putExtra("type", "2");
                startActivityForResult(intent, SELECT_ARTICLE);
                break;
            case R.id.rl_patient_education_article_have_select:
                elArticle.toggle();
                Bitmap bitmapArticle = ImageUtils.getBitmap(R.drawable.right_arrow);
                int widthArticle = bitmapArticle.getWidth();
                int heightArticle = bitmapArticle.getHeight();
                if (elArticle.isExpanded()) {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmapArticle, 90, widthArticle, heightArticle);
                    imgRightArrowArticle.setImageBitmap(resizedBitmap);
                } else {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmapArticle, 0, widthArticle, heightArticle);
                    imgRightArrowArticle.setImageBitmap(resizedBitmap);
                }
                break;
            case R.id.bt_send:
                if (!(mainList.size() > 0)) {
                    ToastUtils.showShort("还没有选择消息接收人");
                    return;
                }
                if (!(articleSelectList.size() > 0)) {
                    ToastUtils.showShort("请选择患教文章");
                    return;
                }
                toDoSend();
                break;
        }
    }

    private void toDoSend() {
        //患教文章接受者id集合（例如（1,2,3））
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mainList.size(); i++) {
            int userid = mainList.get(i).getUserid();
            stringBuilder.append(userid);
            stringBuilder.append(",");
        }
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);//截取最后,
        //患教文章id集合（例如（1,2,3））
        StringBuilder stringBuilderArticle = new StringBuilder();
        for (int i = 0; i < articleSelectList.size(); i++) {
            int id = articleSelectList.get(i).getId();
            stringBuilderArticle.append(id);
            stringBuilderArticle.append(",");
        }
        String substringArticle = stringBuilderArticle.substring(0, stringBuilderArticle.length() - 1);//截取最后,
        HashMap<String, Object> map = new HashMap<>();
        map.put("articleids", substringArticle);
        map.put("receiveuids", substring);
        RxHttp.postForm(XyUrl.SEND_ARTICLE_MSG)
                .addAll(map)
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("添加成功");//跳转消息历史记录
                        startActivity(new Intent(getPageContext(), PatientEducationHistoryListActivity.class));
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }


    @Override
    protected void receiveEvent(EventMessage event) {
        super.receiveEvent(event);
        super.receiveEvent(event);
        switch (event.getCode()) {
            case ConstantParam.EventCode.SEND_SELECT_PEOPLE:
                List<GroupUserBean> haveSelectList = (List<GroupUserBean>) event.getData();
                if (haveSelectList != null && haveSelectList.size() > 0) {
                    rvHaveSelectList.setVisibility(View.VISIBLE);
                    mainList.clear();
                    mainList.addAll(haveSelectList);
                    selectAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case HAVE_SELECT:
                    mainList.clear();//清空原有数据
                    List<GroupUserBean> listObjHaveSelect = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    if (listObjHaveSelect != null && listObjHaveSelect.size() > 0) {
                        rvHaveSelectList.setVisibility(View.VISIBLE);
                        mainList.addAll(listObjHaveSelect);
                        selectAdapter.notifyDataSetChanged();
                    } else {
                        rvHaveSelectList.setVisibility(View.GONE);
                    }
                    break;
                //选择患教文章
                case SELECT_ARTICLE:
                    articleSelectList.clear();
                    //设置患教文章展开
                    elArticle.setExpanded(true);
                    //ArrayList<String> articleSelect = data.getStringArrayListExtra("articleSelect");
                    int selectPosition = data.getIntExtra("selectPosition", 0);
                    //                    ArrayList<PatientEducationArticleListBean.DataBean> articleList =
                    //                            (ArrayList<PatientEducationArticleListBean.DataBean>) data.getSerializableExtra("articleList");
                    PatientEducationArticleListBean.DataBean articleList = (PatientEducationArticleListBean.DataBean) data.getSerializableExtra("articleList");
                    //                    PatientEducationArticleListBean.DataBean dataBean = articleList.get(selectPosition);
                    articleSelectList.add(articleList);
                    lvArticleList.setAdapter(new PatientEducationArticleLvAdapter(getPageContext(), R.layout.item_patient_education_article, articleSelectList));
                    break;
                default:
                    break;
            }
        }
    }
}
