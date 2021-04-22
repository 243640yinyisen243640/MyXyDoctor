package com.xy.xydoctor.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.xy.xydoctor.R;
import com.xy.xydoctor.bean.FollowUpVisitLvBean;

import java.util.HashMap;
import java.util.List;

public class FollowUpVisitLvAdapterThree2 extends BaseAdapter {
    private static final String TAG = "FollowUpVisitBloodSugarSubmitActivity";
    public HashMap<Integer, String> saveMapName;//这个集合用来存储对应位置上Editext中的文本内容
    public HashMap<Integer, String> saveMapCount;//这个集合用来存储对应位置上Editext中的文本内容
    public HashMap<Integer, String> saveMapDosage;//这个集合用来存储对应位置上Editext中的文本内容
    private Context context;
    private List<FollowUpVisitLvBean> list;
    private List<List<String>> medicineList;


    public FollowUpVisitLvAdapterThree2(Context context, List list, List<List<String>> medicineList) {
        this.context = context;
        this.list = list;
        this.medicineList = medicineList;

        initHashMap(medicineList);
    }

    private void initHashMap(List<List<String>> medicineList) {
        //更新
        saveMapName = new HashMap<>();
        saveMapCount = new HashMap<>();
        saveMapDosage = new HashMap<>();
        if (medicineList != null && medicineList.size() > 0) {
            for (int i = 0; i < medicineList.size(); i++) {
                List<String> list = medicineList.get(i);
                for (int j = 0; j < list.size(); j++) {
                    String s = list.get(j);
                    if (0 == j) {
                        saveMapName.put(i, s);
                    }
                    if (1 == j) {
                        saveMapCount.put(i, s);
                    }
                    if (2 == j) {
                        saveMapDosage.put(i, s);
                    }
                }
            }
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.include_follow_up_visit_drug_item, parent, false);
            viewHolder.tvDrugName = convertView.findViewById(R.id.tv_drug_name);
            viewHolder.etName = convertView.findViewById(R.id.et_name);
            viewHolder.etCount = convertView.findViewById(R.id.et_count);
            viewHolder.etDosage = convertView.findViewById(R.id.et_dosage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        int count = position + 1;
        viewHolder.tvDrugName.setText("药品名称" + count);
        //设置第一名称
        viewHolder.etName.setTag(position);//设置editext一个标记
        viewHolder.etName.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etName.setText(saveMapName.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextName = viewHolder.etName;
        viewHolder.etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextName.getTag();
                saveMapName.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //输入设置第二
        viewHolder.etCount.setTag(position);//设置editext一个标记
        viewHolder.etCount.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etCount.setText(saveMapCount.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextCount = viewHolder.etCount;
        viewHolder.etCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextCount.getTag();
                saveMapCount.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });
        //输入设置第三
        //输入设置第二
        viewHolder.etDosage.setTag(position);//设置editext一个标记
        viewHolder.etDosage.clearFocus();//清除焦点 不清除的话因为item复用的原因 多个Editext同时改变
        viewHolder.etDosage.setText(saveMapDosage.get(position));//将对应保存在集合中的文本内容取出来 并显示上去
        final EditText tempEditTextDosage = viewHolder.etDosage;
        viewHolder.etDosage.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Integer tag = (Integer) tempEditTextDosage.getTag();
                saveMapDosage.put(tag, s.toString());//在这里根据position去保存文本内容
            }
        });


        return convertView;
    }


    public class ViewHolder {
        TextView tvDrugName;
        EditText etName;
        EditText etCount;
        EditText etDosage;
    }
}
