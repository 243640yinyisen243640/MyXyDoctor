package com.lyd.baselib.util.eventbus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述: EventBus注解类,继承BaseEventBusActivity后,使用该注解后,只需重写receiveEvent或者receiveStickyEvent方法来进行事件的处理
 * 作者: LYD
 * 创建日期: 2018/6/13 10:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)//反射
public @interface BindEventBus {

}
