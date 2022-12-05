package com.cws.pojo;

import lombok.Data;

/**
 * 天气实体类
 * {"status":0,"result":{"location":{"country":"中国","province":"安徽省","city":"合肥市","name":"合肥","id":"340100"},"now":{"text":"阴","temp":28,"feels_like":29,"rh":88,"wind_class":"4级","wind_dir":"西北风","uptime":"20220823083500"},"forecasts":[{"text_day":"雷阵雨","text_night":"多云","high":33,"low":24,"wc_day":"3~4级","wd_day":"北风","wc_night":"3~4级","wd_night":"东北风","date":"2022-08-23","week":"星期二"},{"text_day":"多云","text_night":"晴","high":30,"low":24,"wc_day":"3~4级","wd_day":"东北风","wc_night":"<3级","wd_night":"东北风","date":"2022-08-24","week":"星期三"},{"text_day":"阴","text_night":"雷阵雨","high":33,"low":24,"wc_day":"3~4级","wd_day":"东北风","wc_night":"3~4级","wd_night":"东北风","date":"2022-08-25","week":"星期四"},{"text_day":"小雨","text_night":"小雨","high":31,"low":23,"wc_day":"<3级","wd_day":"东风","wc_night":"3~4级","wd_night":"东北风","date":"2022-08-26","week":"星期五"},{"text_day":"小雨","text_night":"小雨","high":24,"low":22,"wc_day":"3~4级","wd_day":"东风","wc_night":"3~4级","wd_night":"东风","date":"2022-08-27","week":"星期六"}]},"message":"success"}
 *
 * @author cws
 */
@Data
public class Weather {
    private String text_day;
    private String text_night;
    //  最高气温
    private String high;
    //  最低气温
    private String low;
    //  风力"3-4级"
    private String wc_day;
    //  风向"东北风"
    private String wd_day;
    private String wc_night;
    private String wd_night;
    private String date;
    private String week;
    // 当前天气
    private String text_now;
    // 当前温度
    private String temp;
    // 当前城市
    private String city;
    // 具体城市(到县级)
    private String cityName;
}