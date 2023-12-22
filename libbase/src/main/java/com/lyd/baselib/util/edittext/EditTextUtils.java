package com.lyd.baselib.util.edittext;

import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 描述: EditText常用操作
 * 作者: LYD
 * 创建日期: 2019/12/20 16:26
 */
public class EditTextUtils {

    /**
     * 限制小数点后面的位数和总的位数
     *
     * @param et         输入框
     * @param decimalNum 限制小数点后面的位数和总的位数
     * @param totalNum   总的位数
     */
    public static void decimalNumber(final EditText et, final int decimalNum, final int totalNum) {
        InputFilter lengthFilter = (source, start, end, dest, dstart, dend) -> {
            // source:当前输入的字符
            // start:输入字符的开始位置
            // end:输入字符的结束位置
            // dest：当前已显示的内容
            // dstart:当前光标开始位置
            // dent:当前光标结束位置
            if (dest.length() == 0 && source.equals(".")) {
                return "0.";
            }
            String dValue = dest.toString();
            if (dValue.length() >= totalNum) {
                return "";
            }
            String[] splitArray = dValue.split("\\.");
            if (splitArray.length > 1) {
                String dotValue = splitArray[1];
                String content = et.getText().toString().trim();
                int index = content.indexOf(".");
                if (dotValue.length() == decimalNum) {
                    if (index < dstart) {
                        return "";
                    }

                }
            }
            return null;
        };
        et.setFilters(new InputFilter[]{lengthFilter});
    }

    private EditTextUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 搜索回调
     *
     * @param et
     * @param listener
     */
    public static void setOnActionSearch(EditText et, OnActionSearchListener listener) {
        et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    listener.onActionSearch();
                    return true;
                }
                return false;
            }
        });
    }

    ///////////////////////////////////////////////////////////////////////////
    // interface
    ///////////////////////////////////////////////////////////////////////////
    public interface OnActionSearchListener {
        void onActionSearch();
    }
}
