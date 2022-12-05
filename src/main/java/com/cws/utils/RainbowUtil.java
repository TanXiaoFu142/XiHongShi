package com.cws.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cws.configure.PushConfigure;
import com.cws.pojo.Result;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 彩虹屁接口调用
 *
 * @author cws
 */
public class RainbowUtil {
    public static Result getRainbow() {
        String httpUrl = "http://api.tianapi.com/caihongpi/index?key=" + PushConfigure.getRainbowKey();
        BufferedReader reader = null;
        String rs = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                stringBuilder.append(strRead);
                stringBuilder.append("\r\n");
            }
            reader.close();
            rs = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(rs);

        Result result = new Result();
        if (jsonObject == null) {
            //接口地址有误或者接口没调通
            result.setCode("500");
            result.setMessage("天行数据接口不通,请检查接口地址!");
            return result;
        }
//            获取接口响应状态
        String code = jsonObject.getString("code");
        if (!"200".equals(code)) {
//            如果响应状态不为200,则调用出错
            String msg = jsonObject.getString("msg");
            result.setCode(code);
            result.setMessage("天行数据接口调用报错:" + msg);
            return result;
        }
        JSONArray newslist = jsonObject.getJSONArray("newslist");
        String data = "\n" + newslist.getJSONObject(0).getString("content");
        result.setCode(code);
        result.setData(data);
        return result;
    }

}