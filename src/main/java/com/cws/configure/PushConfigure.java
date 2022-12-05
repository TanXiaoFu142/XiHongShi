package com.cws.configure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * PushConfigure
 *
 * @author cws
 * @date 2022/8/22 21:25
 */
@Component
@ConfigurationProperties("wechat")
public class PushConfigure {
    /**
     * 微信公众平台的appID
     */
    private static String appId;
    /**
     * 微信公众平台的appSecret
     */
    private static String secret;
    /**
     * 天气查询的城市ID
     */
    private static List<String> district_id;
    /**
     * 应用AK
     */
    private static String ak;
    /**
     * 纪念日
     */
    private static String loveDate;
    /**
     * 关注公众号的用户ID
     */
    private static List<String> userId;
    /**
     * 模板ID
     */
    private static String templateId;

    /**
     * 天行数据apiKey
     */
    private static String rainbowKey;

    /**
     * 是否使用农历计算生日
     */
    private static boolean useLunar;

    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        PushConfigure.appId = appId;
    }

    public static String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        PushConfigure.secret = secret;
    }

    public static List<String> getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(List<String> district_id) {
        PushConfigure.district_id = district_id;
    }

    public static String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        PushConfigure.ak = ak;
    }

    public static String getLoveDate() {
        return loveDate;
    }

    public void setLoveDate(String loveDate) {
        PushConfigure.loveDate = loveDate;
    }

    public static List<String> getUserId() {
        return userId;
    }

    public void setUserId(List<String> userId) {
        PushConfigure.userId = userId;
    }

    public static String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        PushConfigure.templateId = templateId;
    }

    public static String getRainbowKey() {
        return rainbowKey;
    }


    public void setRainbowKey(String rainbowKey) {
        PushConfigure.rainbowKey = rainbowKey;
    }

    public static boolean isUseLunar() {
        return useLunar;
    }

    public void setUseLunar(boolean useLunar) {
        PushConfigure.useLunar = useLunar;
    }
}
