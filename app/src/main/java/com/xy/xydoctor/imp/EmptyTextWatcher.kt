package com.xy.xydoctor.imp

import android.text.Editable
import android.text.TextWatcher

/**
 * 描述:设计模式之适配器模式的一种
 * 接口适配器模式
 * 作者: LYD
 * 创建日期: 2021/1/13 11:04
 */
open class EmptyTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}