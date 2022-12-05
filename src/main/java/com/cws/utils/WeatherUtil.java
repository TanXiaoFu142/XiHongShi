package com.cws.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cws.configure.PushConfigure;
import com.cws.pojo.Result;
import com.cws.pojo.Weather;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取天气数据
 *
 * @author cws
 */
@Component
public class WeatherUtil {
    public static Result getWeather(String districtId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("district_id", districtId);
        map.put("ak", PushConfigure.getAk());
        String res = restTemplate.getForObject("https://api.map.baidu.com/weather/v1/?district_id={district_id}&data_type=all&ak={ak}", String.class, map);
        JSONObject json = JSONObject.parseObject(res);

        Result result = new Result();
        if (json == null) {
            //接口地址有误或者接口没调通
            result.setCode("500");
            result.setMessage("接口不通,请检查接口地址!");
            return result;
        }
//            获取接口响应状态
        String status = json.getString("status");
        if (!"0".equals(status)) {
//            响应状态不为0即调用出错
            String err = json.getString("message");
            result.setCode(status);
            result.setMessage("天气接口调用报错:" + err);
            return result;
        }
        JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
        List<Weather> weathers = forecasts.toJavaList(Weather.class);
        Weather weather = weathers.get(0);
        JSONObject now = json.getJSONObject("result").getJSONObject("now");
        JSONObject location = json.getJSONObject("result").getJSONObject("location");
        weather.setText_now(now.getString("text"));
        weather.setTemp(now.getString("temp"));
        weather.setCity(location.getString("city"));
        weather.setCityName(location.getString("name"));
        result.setCode(status);
        result.setData(weather);
        return result;
    }
}
