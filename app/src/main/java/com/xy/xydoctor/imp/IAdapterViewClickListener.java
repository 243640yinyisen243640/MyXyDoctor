package com.xy.xydoctor.imp;

import android.view.View;

/**
 * Author: LYD
 * Date: 2021/6/22 17:39
 * Description:
 */
public interface IAdapterViewClickListener {
    void adapterClickListener(int position, View view);
    void adapterClickListener(int position, int index, View view);
}
