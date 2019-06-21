package com.lib.apihosts;

/**
 * @author zhangshuai
 */
public class ApiService {

    /**
     * 当前环境
     */
    @ApiHost
    public static String CURRENT_API = ApiHost.API_HOST_RELEASE;

    /**
     * 是否是测试环境
     */
    public static boolean isTest() {
        return ApiHost.API_HOST_DEBUG.equals(CURRENT_API);
    }

    /**
     * 是否是测试环境
     */
    public static boolean isBeta() {
        return ApiHost.API_HOST_BETA.equals(CURRENT_API);
    }

    /**
     * 获取接口域名
     */
    @ApiHost
    public static String getApihost() {
        return CURRENT_API;
    }

    /**
     * 设置接口域名
     */
    @ApiHost
    public static String setApihost(@ApiHost String apiHost) {
        return CURRENT_API = apiHost;
    }

}
