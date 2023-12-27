package com.xy.xydoctor.ui.activity.patienteducation;

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
import com.blankj.utilcode.util.Utils;
import com.lyd.baselib.widget.view.MyListView;
import com.lyd.librongim.myrongim.GroupUserBean;
import com.rxjava.rxlife.RxLife;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.MassMsgGroupListAdapter;
import com.xy.xydoctor.adapter.PatientEducationArticleLvAdapter;
import com.xy.xydoctor.adapter.SelectUserAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.GroupListBean;
import com.xy.xydoctor.bean.PatientEducationArticleListBean;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgHaveSelectPeopleActivity;
import com.xy.xydoctor.ui.activity.massmsg.MassMsgSelectPeopleActivity;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  患教主页
 * 作者: LYD
 * 创建日期: 2019/6/1 11:17
 */
public class PatientEducationMainActivity extends BaseActivity {
    private static final int SELECT = 10010;
    private static final int HAVE_SELECT = 10011;
    private static final int SELECT_ARTICLE = 10086;
    private static final String TAG = "PatientEducationMainActivity";

    @BindView(R.id.img_right_arrow_patient_education)
    ImageView imgRightArrowPatientEducation;
    @BindView(R.id.rl_patient_education)
    RelativeLayout rlPatientEducation;
    @BindView(R.id.lv_patient_education)
    MyListView lvPatientEducation;
    @BindView(R.id.el_patient_education)
    ExpandableLayout elPatientEducation;

    @BindView(R.id.img_right_arrow)
    ImageView imgRightArrow;
    @BindView(R.id.rl_have_select)
    RelativeLayout rlHaveSelect;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.img_right_arrow_article_have_select)
    ImageView imgRightArrowArticle;
    @BindView(R.id.rl_patient_education_article)
    RelativeLayout rlPatientEducationArticle;
    @BindView(R.id.lv_article_list)
    MyListView lvArticleList;
    @BindView(R.id.el_article)
    ExpandableLayout elArticle;

    @BindView(R.id.bt_send)
    Button btSend;

    private ArrayList<GroupUserBean> mainList;
    private SelectUserAdapter adapter;
    private ArrayList<PatientEducationArticleListBean.DataBean> articleSelectList = new ArrayList<>();
    private List<GroupListBean> data;


    /**
     * 初始化展开
     */
    private void initExpandableLayout() {
        //elArticle.setExpanded(true);
        //elPatientEducation.setExpanded(true);
    }

    private void initRv() {
        mainList = new ArrayList<>();
        //构造 LinearLayputManager，并设置方向。LinearLayputManager效果类似ListView。
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置滑动方向：横向
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(layoutManager);
        adapter = new SelectUserAdapter(mainList);
        rvList.setAdapter(adapter);
        //adapter.setOnItemClickListener(this);
        rvList.setVisibility(View.GONE);
    }


    /**
     * 获取教育分组
     */
    private void getGroupList() {
        HashMap<String, Object> map = new HashMap<>();
        RxHttp.postForm(XyUrl.GET_GROUP_LIST)
                .addAll(map)
                .asResponseList(GroupListBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<GroupListBean>>() {
                    @Override
                    public void accept(List<GroupListBean> myTreatPlanBeans) throws Exception {
                        data = myTreatPlanBeans;
                        if (myTreatPlanBeans != null) {
                            addData(myTreatPlanBeans);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 设置教育分组
     *
     * @param data
     */
    private void addData(List<GroupListBean> data) {
        MassMsgGroupListAdapter adapter = new MassMsgGroupListAdapter(Utils.getApp(), R.layout.item_mass_msg_group, data);
        lvPatientEducation.setAdapter(adapter);
    }

    /**
     * 标题设置
     */
    private void initTitle() {
        setTitle("患者宣教");
        getTvMore().setText("历史记录");
        getTvMore().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getPageContext(), PatientEducationHistoryListActivity.class));
            }
        });
    }


    @OnClick({
            R.id.rl_have_select,
            R.id.rl_patient_education,
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
            case R.id.rl_patient_education:
                elPatientEducation.toggle();
                Bitmap bitmap = ImageUtils.getBitmap(R.drawable.right_arrow);
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                if (elPatientEducation.isExpanded()) {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 90, width, height);
                    imgRightArrowPatientEducation.setImageBitmap(resizedBitmap);
                } else {
                    Bitmap resizedBitmap = ImageUtils.rotate(bitmap, 0, width, height);
                    imgRightArrowPatientEducation.setImageBitmap(resizedBitmap);
                }
                break;
            case R.id.rl_patient_education_article:
                Intent intent = new Intent(getPageContext(), PatientEducationArticleListActivity.class);
                intent.putExtra("type", "1");
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

    @OnItemClick(R.id.lv_patient_education)
    void onItemClick(int position) {
        GroupListBean item = data.get(position);
        int gid = item.getGid();
        String gname = item.getGname();
        Intent intent = new Intent(getPageContext(), MassMsgSelectPeopleActivity.class);
        intent.putExtra("gid", gid + "");
        intent.putExtra("gname", gname);
        intent.putExtra("type", "3");
        startActivityForResult(intent, SELECT);
    }

    /**
     * 发送
     */
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SELECT:
                    rvList.setVisibility(View.VISIBLE);
                    List<GroupUserBean> listObj = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    mainList.addAll(listObj);
                    adapter.notifyDataSetChanged();
                    break;
                case HAVE_SELECT:
                    mainList.clear();//清空原有数据
                    List<GroupUserBean> listObjHaveSelect = (List<GroupUserBean>) data.getSerializableExtra("selectUserInfo");
                    if (listObjHaveSelect != null && listObjHaveSelect.size() > 0) {
                        rvList.setVisibility(View.VISIBLE);
                        mainList.addAll(listObjHaveSelect);
                        adapter.notifyDataSetChanged();
                    } else {
                        rvList.setVisibility(View.GONE);
                    }
                    break;
                //选择患教文章
                case SELECT_ARTICLE:
                    articleSelectList.clear();
                    //设置患教文章展开
                    elArticle.setExpanded(true);
                    int selectPosition = data.getIntExtra("selectPosition", 0);
                    ArrayList<PatientEducationArticleListBean.DataBean> articleList =
                            (ArrayList<PatientEducationArticleListBean.DataBean>) data.getSerializableExtra("articleList");
                    PatientEducationArticleListBean.DataBean dataBean = articleList.get(selectPosition);
                    articleSelectList.add(dataBean);
                    lvArticleList.setAdapter(new PatientEducationArticleLvAdapter(getPageContext(), R.layout.item_patient_education_article, articleSelectList));
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_education_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initExpandableLayout();
        initTitle();
        getGroupList();
        initRv();
    }
}
