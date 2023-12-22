package com.xy.xydoctor.ui.activity.insulin;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.xy.xydoctor.R;
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity;
import com.xy.xydoctor.datamanager.DataManager;
import com.xy.xydoctor.utils.TipUtils;
import com.xy.xydoctor.view.LinePathView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者: beauty
 * 类名:
 * 传参:
 * 描述:
 */
public class InsulinPlanSignActivity extends XYSoftUIBaseActivity {
    private LinePathView singView;
    private TextView tvClear;
    private TextView tvSure;

    private String userid;
    /**
     * 1：大剂量  2：基础率
     */
    private String type;

    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topViewManager().titleTextView().setText("医生签字");
        userid = getIntent().getStringExtra("userid");
        type = getIntent().getStringExtra("type");
        data = getIntent().getStringExtra("data");
        containerView().addView(initView());
    }

    private View initView() {
        View view = View.inflate(getPageContext(), R.layout.activity_plad_add_doctor_sign, null);
        singView = view.findViewById(R.id.lpv_plan_add_sign);
        tvClear = view.findViewById(R.id.lpv_plan_add_sign_clear);
        tvClear.setOnClickListener(v -> {
            singView.clear();
        });
        tvSure = view.findViewById(R.id.lpv_plan_add_sign_sure);
        tvSure.setOnClickListener(v -> {
            Log.i("yys", "type===" + type);
            Bitmap bitMap = singView.getBitMap1(singView);
            String path = createDir(getPageContext(), "signatureFile" + ".png", "/signatureFile");
            try {
                File file = new File(path);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                bitMap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("yys", "IOException==" + e.getMessage());
                return;
            }
            sendSure(path);
        });
        return view;
    }

    private void sendSure(String path) {
        DataManager.useraddeqplan(type, path, data, userid, (call, response) -> {
            if (response.code == 200) {
                delFile(path);
                finish();
            }
            TipUtils.getInstance().showToast(getPageContext(), response.msg);
        }, (call, t) -> {
            TipUtils.getInstance().showToast(getPageContext(), R.string.network_error);
        });
    }

    /**
     * 创建文件夹
     *
     * @param filename
     * @return
     */
    private String createDir(Context context, String filename, String directory_path) {
        String state = Environment.getExternalStorageState();
        File rootDir = /*state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : */context.getCacheDir();
        File path = null;
        if (!TextUtils.isEmpty(directory_path)) {
            // 自定义保存目录
            path = new File(rootDir.getAbsolutePath() + directory_path);
        } else {
            path = new File(rootDir.getAbsolutePath() + "/PictureSelector");
        }
        if (!path.exists())
            // 若不存在，创建目录，可以在应用启动的时候创建
            path.mkdirs();

        return path + "/" + filename;
    }

    /**
     * delete file
     *
     * @param path
     */
    private void delFile(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                if (file != null) {
                    file.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
