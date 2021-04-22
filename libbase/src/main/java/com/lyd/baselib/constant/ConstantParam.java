package com.lyd.baselib.constant;


/**
 * 描述: 常量接口
 * 作者: LYD
 * 创建日期: 2018/6/12 17:35
 */
public interface ConstantParam {

    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 10;

    /**
     * EventBus Code(静态内部类)
     */
    final class EventCode {
        //患者添加
        public static final int ADD = 1000;
    }
}
