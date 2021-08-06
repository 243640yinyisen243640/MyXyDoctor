package com.xy.xydoctor.ui.activity.community_management

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import com.xy.xydoctor.R
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity

/**
 * Author: LYD
 * Date: 2021/8/6 10:48
 * Description:

 */
class SpecialOperateActivity : XYSoftUIBaseActivity() {
    private var deleteCheckBox: CheckBox? = null
    private var dieCheckBox: CheckBox? = null
    private var submitTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nameUser = intent.getStringExtra("userName")
        topViewManager().backTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_base_back_red, 0, 0, 0)
        topViewManager().titleTextView().text = nameUser
        initView()
    }


    private fun initView() {
        containerView().addView(View.inflate(pageContext, R.layout.activity_community_special_operate, null))
        deleteCheckBox = findViewById(R.id.cb_community_so_delete)
        dieCheckBox = findViewById(R.id.cb_community_so_die)
        submitTextView = findViewById(R.id.tv_community_so_submit)
        submitTextView?.setOnClickListener {

        }
    }


}