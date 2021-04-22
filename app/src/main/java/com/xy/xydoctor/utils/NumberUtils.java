package com.xy.xydoctor.utils;

import java.math.BigDecimal;

/**
 * 描述:Double类型数据处理类
 * 作者: LYD
 * 创建日期: 2019/7/16 10:58
 */
public class NumberUtils {
    private NumberUtils() {
    }

    /**
     * 保留一位小数，进行四舍五入
     *
     * @param d
     * @return
     */
    public static double saveOneBitOneRound(Double d) {
        BigDecimal bd = new BigDecimal(d);
        double tem = bd.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return tem;
    }
}
