package com.xy.xydoctor.imp;


import com.lyd.librongim.myrongim.ImWarningMessage;

import io.rong.imkit.model.UIMessage;

public interface ImWarningClickListener {
    void onCardClick(ImWarningMessage content, UIMessage uiMessage);
}
