package com.xy.xydoctor.view.popup;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wei.android.lib.colorview.view.ColorEditText;
import com.xy.xydoctor.R;

public class LiverFilesInputShowUtils {

    public static void showPopup(Context context, String title, String content, final LiverFilesInputShowUtils.DialogInputCallBack callBack) {
        LiverFilesInputPopup multiLinesInputPopup = new LiverFilesInputPopup(context);
        TextView tvTitle = multiLinesInputPopup.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        ColorEditText etInput = multiLinesInputPopup.findViewById(R.id.et_input);
        etInput.setText(content);
        TextView tvSure = multiLinesInputPopup.findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etInput.getText().toString().trim();
                callBack.execEvent(text);
                multiLinesInputPopup.dismiss();
            }
        });
        multiLinesInputPopup.showPopupWindow();
    }


    public interface DialogInputCallBack {
        void execEvent(String content);
    }
}
