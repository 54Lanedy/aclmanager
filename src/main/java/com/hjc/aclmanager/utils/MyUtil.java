package com.hjc.aclmanager.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by liyue
 * Time 2021/1/12 10:50
 */
public class MyUtil {
    public final static String[] WEEK_DAYS = {"星期日", "星期一", "星期二", "星期三",
            "星期四", "星期五", "星期六"};
    //===========2020-09-17 使用ThreadLocal存储指定线程数据，避免多线程编程出现SimpleDateFormat线程不安全问题===========
    public final static SimpleDateFormat DATE_F = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)).get();
    // 时间格式1
    /**
     * yyyy-MM-dd HH:mm
     */
    public final static SimpleDateFormat DATE_F1 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)).get();
    // 时间格式2
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static SimpleDateFormat DATE_F2 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)).get();

    // 时间格式3
    /**
     * 莫建有 begin 2018-5-12
     * HH:mm
     */
    public final static SimpleDateFormat DATE_F3 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("HH:mm", Locale.CHINA)).get();

    /**
     * yyyy-MM
     */
    public final static SimpleDateFormat DATE_F4 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM", Locale.CHINA)).get();
    /**
     * 莫建有 end 2018-5-12
     */

    /**
     * yy年MM月dd日
     */
    public final static SimpleDateFormat DATE_F6 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yy年MM月dd日", Locale.CHINA)).get();
    /**
     * yyyyMMddHHmmss
     */
    public final static SimpleDateFormat DATE_F8 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA)).get();
    /**
     * yyMMddHHmmssSSS
     */
    public final static Format DATE_F9 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyMMddHHmmssSSS", Locale.CHINA)).get();
    /**
     * yyyyMM
     */
    public final static Format DATE_F10 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyyMM", Locale.CHINA)).get();

    /**
     * yyyy-MM-dd HH:mm:ss:SSS
     */
    public final static SimpleDateFormat DATE_F11 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS", Locale.CHINA)).get();

    public final static Format DATE_F13 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyMMddHHmmss", Locale.CHINA)).get();

    public final static Format DATE_F14 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("HH:mm", Locale.CHINA)).get();

    public final static Format DATE_F15 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy", Locale.CHINA)).get();

    public final static SimpleDateFormat DATE_F16 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-M-d", Locale.CHINA)).get();

    public final static SimpleDateFormat DATE_F17 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("yyyy-MM", Locale.CHINA)).get();
    public final static SimpleDateFormat DATE_F18 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("MM", Locale.CHINA)).get();

    public final static Format DATE_F19 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("HHmmss", Locale.CHINA)).get();

    public final static SimpleDateFormat DATE_F20 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA)).get();

    public final static SimpleDateFormat DATE_F21 = ThreadLocal.withInitial(() ->
            new SimpleDateFormat("HH", Locale.CHINA)).get();


    /**
     * 获取web物理路径
     *
     * @return
     */
    public final static String getRootPath() {
        String str = MyUtil.class.getClassLoader().getResource("").getPath();
        return str.substring(1, str.length()).split("WEB-INF")[0];
    }

    /**
     * 获取格式后的ID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
