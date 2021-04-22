package com.xy.xydoctor.ui.activity.homesign;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lyd.baselib.util.eventbus.EventBusUtils;
import com.lyd.baselib.util.eventbus.EventMessage;
import com.rxjava.rxlife.RxLife;
import com.wei.android.lib.colorview.view.ColorTextView;
import com.xy.xydoctor.R;
import com.xy.xydoctor.adapter.FamilyUserAdapter;
import com.xy.xydoctor.base.activity.BaseActivity;
import com.xy.xydoctor.bean.FamilySelectUserBean;
import com.xy.xydoctor.constant.ConstantParam;
import com.xy.xydoctor.net.ErrorInfo;
import com.xy.xydoctor.net.OnError;
import com.xy.xydoctor.net.XyUrl;
import com.xy.xydoctor.utils.PickerUtils;
import com.xy.xydoctor.view.popup.FamilySelectUserPopup;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.functions.Consumer;
import rxhttp.wrapper.param.RxHttp;

/**
 * 描述:  远程签约
 * 作者: LYD
 * 创建日期: 2019/12/30 15:26
 */
public class SignRemoteActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int SIGN_ADD_DOCTOR = 10087;
    private static final String TAG = "SignRemoteActivity";

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_relation)
    TextView tvRelation;
    @BindView(R.id.et_id_number)
    EditText etIdNumber;
    @BindView(R.id.et_phone)
    EditText etPhone;

    @BindView(R.id.ll_select_family_group)
    LinearLayout llSelectFamilyGroup;
    @BindView(R.id.tv_select_family_group)
    TextView tvSelectFamilyGroup;

    @BindView(R.id.img_sign_add_doctor)
    ImageView imgSignAddDoctor;
    @BindView(R.id.tv_sign_add_doctor)
    TextView tvSignAddDoctor;

    @BindView(R.id.tv_submit)
    ColorTextView tvSubmit;
    //选择家庭组开始
    private FamilySelectUserPopup familySelectUserPopup;
    private EditText etSearch;
    private TextView tvSearch;
    private ListView lvList;
    private FamilyUserAdapter adapter;
    private List<FamilySelectUserBean> list;
    private int familyId;
    //选择家庭组结束

    private String relationId;
    private String filePath;


    /**
     * 设置是否从家庭列表中来
     */
    private void setIsFamily() {
        int family_id = getIntent().getIntExtra("family_id", -1);
        if (-1 == family_id) {
            llSelectFamilyGroup.setVisibility(View.VISIBLE);
        } else {
            llSelectFamilyGroup.setVisibility(View.GONE);
        }
    }

    /**
     * 获取家庭签约患者列表
     *
     * @param keyWord
     */
    private void getFamilyUsers(String keyWord) {
        HashMap<String, String> map = new HashMap<>();
        map.put("keyword", keyWord);
        RxHttp.postForm(XyUrl.FAMILY_LIST)
                .addAll(map)
                .asResponseList(FamilySelectUserBean.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<List<FamilySelectUserBean>>() {
                    @Override
                    public void accept(List<FamilySelectUserBean> familySelectUserBeanList) throws Exception {
                        list = familySelectUserBeanList;
                        if (list != null && list.size() > 0) {
                            adapter = new FamilyUserAdapter(getPageContext(), R.layout.item_family_user, list);
                            lvList.setAdapter(adapter);
                        }
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });
    }

    /**
     * 弹窗
     */
    private void initPopup() {
        familySelectUserPopup = new FamilySelectUserPopup(getPageContext());
        etSearch = familySelectUserPopup.findViewById(R.id.et_search);
        tvSearch = familySelectUserPopup.findViewById(R.id.tv_search);
        lvList = familySelectUserPopup.findViewById(R.id.lv_list);
        lvList.setOnItemClickListener(this);
        tvSearch.setOnClickListener(this);
    }


    @OnClick({R.id.tv_relation, R.id.ll_select_family_group, R.id.img_sign_add_doctor, R.id.tv_submit})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //选择关系
            case R.id.tv_relation:
                KeyboardUtils.hideSoftInput(tvRelation);
                showSelectRelation();
                break;
            //选择家庭组
            case R.id.ll_select_family_group:
                familySelectUserPopup.showPopupWindow();
                break;
            case R.id.img_sign_add_doctor:
                intent = new Intent(getPageContext(), SignatureEditActivity.class);
                startActivityForResult(intent, SIGN_ADD_DOCTOR);
                break;
            case R.id.tv_submit:
                doSubmit();
                break;
        }
    }

    private void doSubmit() {
        String name = etName.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(filePath)) {
            ToastUtils.showShort("请添加医生签名照片");
            return;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("nickname", name);
        map.put("relation", relationId);
        map.put("idcard", idNumber);
        map.put("tel", phone);
        String url;
        int family_id = getIntent().getIntExtra("family_id", -1);
        if (-1 == family_id) {
            map.put("familyid", familyId);
            map.put("type", "1");
            url = XyUrl.FAMILY_DOCTOR_ADD;
        } else {
            map.put("familyid", family_id);
            url = XyUrl.FAMILY_MEMBER_ADD;
        }
        RxHttp.postForm(url)
                .addAll(map)
                .addFile("doc_sign", new File(filePath))
                .asResponse(String.class)
                .to(RxLife.toMain(this))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String response) throws Exception {
                        ToastUtils.showShort("操作成功");
                        EventBusUtils.post(new EventMessage<Integer>(ConstantParam.EventCode.FAMILY_MEMBER_ADD));
                        finish();
                    }
                }, new OnError() {
                    @Override
                    public void onError(ErrorInfo error) throws Exception {

                    }
                });

    }

    private void showSelectRelation() {
        String[] stringArray = getResources().getStringArray(R.array.home_sign_relation);
        List<String> relationList = ArrayUtils.asList(stringArray);
        PickerUtils.showOptionPosition(getPageContext(), new PickerUtils.PositionCallBack() {
            @Override
            public void execEvent(String content, int position) {
                tvRelation.setText(content);
                relationId = position + 1 + "";
            }
        }, relationList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case SIGN_ADD_DOCTOR:
                    filePath = data.getStringExtra("savePath");
                    Log.e(TAG, "filePath==" + filePath);
                    Glide.with(Utils.getApp()).load(filePath).into(imgSignAddDoctor);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                Log.e(TAG, "onClick");
                String keyWord = etSearch.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    ToastUtils.showShort("请输入手机号或者身份证号");
                    return;
                }
                getFamilyUsers(keyWord);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e(TAG, "onItemClick");
        for (FamilySelectUserBean data : list) {
            data.setSelected(false);
        }
        //点击的设置为选中
        FamilySelectUserBean data = list.get(position);
        data.setSelected(true);
        //刷新
        adapter.notifyDataSetChanged();
        //设置值
        String nickname = data.getNickname();
        tvSelectFamilyGroup.setText(nickname);
        familyId = data.getId();
        familySelectUserPopup.dismiss();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_remote;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("新增编辑");
        setIsFamily();
        getFamilyUsers("");
        initPopup();
    }
}
