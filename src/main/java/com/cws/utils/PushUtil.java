package com.cws.utils;

import com.cws.DateUtils.LunarCalendarFestivalUtils;
import com.cws.configure.PushConfigure;
import com.cws.configure.User;
import com.cws.configure.UserConfigs;
import com.cws.pojo.Result;
import com.cws.pojo.Weather;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpTemplateMsgService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * PushUtil
 *
 * @author cws
 * @date 2022/8/22 21:40
 */
public class PushUtil {

    private static WxMpTemplateMsgService wxService = null;

    /**
     * 消息推送主要业务代码
     */
    public String push() {
        // 构建模板消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .templateId(PushConfigure.getTemplateId())
                .build();
        // 计算天数
        long loveDays = MemoryDayUtil.calculationLianAi(PushConfigure.getLoveDate());
        long birthdays = 0;
        List<User> userList = UserConfigs.getUsers();
        // 备注
        StringBuilder remark = new StringBuilder("❤");
        if (loveDays % 365 == 0) {
            remark.append("\n今天是相遇" + (loveDays / 365) + "周年纪念日!");
        }

        if (PushConfigure.isUseLunar()) {
            // 如果使用农历生日
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                birthdays = MemoryDayUtil.calculationBirthdayByLunar(user.getBirthday());
                templateMessage.addData(new WxMpTemplateData("name"+i, user.getName() + "", "#FFA500"));
                templateMessage.addData(new WxMpTemplateData("birthdays"+i, birthdays + "", "#FFA500"));
                if (birthdays == 0) {
                    remark .append("\n今天是"+user.getName()+"生日,生日快乐呀!");
                }
                if (loveDays % 365 == 0 && birthdays == 0) {
                    remark.append("\n今天是"+user.getName()+"生日,也是相遇" + (loveDays / 365) + "周年纪念日!");
                }
            }
        } else {
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                birthdays = MemoryDayUtil.calculationBirthdayByLunar(user.getBirthday());
                templateMessage.addData(new WxMpTemplateData("name"+i, user.getName() + "", "#FFA500"));
                templateMessage.addData(new WxMpTemplateData("birthdays"+i, birthdays + "", "#FFA500"));
                if (birthdays == 0) {
                    remark .append("\n今天是"+user.getName()+"生日,生日快乐呀!");
                }
                if (loveDays % 365 == 0 && birthdays == 0) {
                    remark.append("\n今天是"+user.getName()+"生日,也是相遇" + (loveDays / 365) + "周年纪念日!");
                }
            }
        }
        templateMessage.addData(new WxMpTemplateData("loveday", loveDays + "", "#FF1493"));

        // 获取天气数据
        List<String> districtIdList = PushConfigure.getDistrict_id();
        StringBuilder messageAll = new StringBuilder();
        for (int i = 0; i < districtIdList.size(); i++) {
            String districtId = districtIdList.get(i);
            Result weatherResult = WeatherUtil.getWeather(districtId);
            if (!"0".equals(weatherResult.getCode())) {
                messageAll.append("<br/>");
                messageAll.append(weatherResult.getMessage());
                templateMessage.addData(new WxMpTemplateData("weather", "***", "#00FFFF"));
            } else {
                Weather weather = (Weather) weatherResult.getData();
                // 拿到农历日期
                LunarCalendarFestivalUtils festival = new LunarCalendarFestivalUtils();
                festival.initLunarCalendarInfo(weather.getDate());

                //城市名称
                String cityName = weather.getCityName();
                //天气
                String textNow = weather.getText_now();
                //最低温度+最高温度
                String tRange = weather.getLow() + " ~ " + weather.getHigh();
                //风向
                String windDirection = weather.getWc_day();

                templateMessage.addData(new WxMpTemplateData("city"+i, cityName + "", "#173177"));
                templateMessage.addData(new WxMpTemplateData("textnow"+i, textNow, "#00FFFF"));
                templateMessage.addData(new WxMpTemplateData("trange"+i, tRange + "", "#FF6347"));
                templateMessage.addData(new WxMpTemplateData("windDirection"+i,windDirection));
                //时间
                templateMessage.addData(new WxMpTemplateData("date", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
                templateMessage.addData(new WxMpTemplateData("lunar", "农历" + festival.getLunarYear() + "年 " + festival.getLunarMonth() + "月" + festival.getLunarDay(), "#00BFFF"));
            }
        }

        templateMessage.addData(new WxMpTemplateData("remark", remark.toString(), "#FF1493"));

        // 天行数据接口
        Result rainbowResult = RainbowUtil.getRainbow();
        if (!"200".equals(rainbowResult.getCode())) {
            messageAll.append("<br/>");
            messageAll.append(rainbowResult.getMessage());
        } else {
            templateMessage.addData(new WxMpTemplateData("rainbow", (String) rainbowResult.getData(), "#FF69B4"));
        }


        System.out.println(templateMessage.toJson());

        // 拿到service
        WxMpTemplateMsgService service = getService();

        int suc = 0;
        int err = 0;
        for (String userId : PushConfigure.getUserId()) {
            templateMessage.setToUser(userId);
            try {
                service.sendTemplateMsg(templateMessage);
                suc += 1;
            } catch (WxErrorException e) {
                err += 1;
                messageAll.append(suc).append("个成功!");
                messageAll.append(err).append("个失败!");
                messageAll.append("<br/>");
                messageAll.append(e.getMessage());
                return "推送结果:" + messageAll;
            }
        }

        return "成功推送给" + suc + "个用户!" + messageAll;
    }

    /**
     * 获取 WxMpTemplateMsgService
     *
     * @return WxMpTemplateMsgService
     */
    private static WxMpTemplateMsgService getService() {
        if (wxService != null) {
            return wxService;
        }
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId(PushConfigure.getAppId());
        wxStorage.setSecret(PushConfigure.getSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);
        wxService = wxMpService.getTemplateMsgService();
        return wxService;
    }
}
