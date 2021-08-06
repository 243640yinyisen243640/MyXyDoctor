package com.xy.xydoctor.ui.activity.community_management

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.xy.xydoctor.R
import com.xy.xydoctor.base.activity.XYSoftUIBaseActivity

/**
 * Author: LYD
 * Date: 2021/8/6 10:48
 * Description:

 */
class SettingsPropertyActivity : XYSoftUIBaseActivity(), View.OnClickListener {
    private var versionLinerLayout: TextView? = null
    private var versionTextView: TextView? = null
    private var outLoginTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        topViewManager().titleTextView().text = getString(R.string.community_setting_title)
        topViewManager().backTextView().setCompoundDrawablesWithIntrinsicBounds(R.drawable.img_base_back_red, 0, 0, 0)
        initView()
        initListener()
    }

    private fun initListener() {
        versionLinerLayout?.setOnClickListener(this)
        outLoginTextView?.setOnClickListener(this)
    }


    private fun initView() {
        containerView().addView(View.inflate(pageContext, R.layout.activity_community_setting, null))
        versionLinerLayout = findViewById(R.id.ll_community_out_version)
        versionTextView = findViewById(R.id.tv_community_out_version)
        outLoginTextView = findViewById(R.id.tv_community_out_login)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.ll_community_out_version -> {

            }
            R.id.tv_community_out_version -> {

            }
            else -> {

            }
        }
    }



}