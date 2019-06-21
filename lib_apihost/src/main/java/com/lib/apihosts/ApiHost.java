package com.lib.apihosts;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author zhangshuai
 */
@StringDef({ApiHost.API_HOST_RELEASE,ApiHost.API_HOST_DEBUG,ApiHost.API_HOST_BETA})
@Retention(RetentionPolicy.SOURCE)
public @interface ApiHost {
    /**
     * 发布环境
     */
    String API_HOST_RELEASE="http://api.com/";
    /**
     * 测试环境
     */
    String API_HOST_DEBUG="http://test.api.com/";
    /**
     * 预发布环境
     */
    String API_HOST_BETA="http://beta.api.com/";
}
