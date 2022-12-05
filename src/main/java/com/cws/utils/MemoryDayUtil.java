package com.cws.utils;


import com.cws.DateUtils.Lunar;
import com.cws.DateUtils.LunarSolarConverter;
import com.cws.DateUtils.Solar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 纪念日工具类
 *
 * @author cws
 */
public class MemoryDayUtil {
    private static final ThreadLocal<SimpleDateFormat> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 获取SimpleDateFormat
     *
     * @return
     */
    private static SimpleDateFormat get() {
        SimpleDateFormat sdf = THREAD_LOCAL.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            THREAD_LOCAL.set(sdf);
        }
        return sdf;
    }

    /**
     * 计算两个时间差
     */
    public static long getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
//        long nh = 1000 * 60 * 60;
//        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
//        long hour = diff % nd / nh;
        // 计算差多少分钟
//        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day;
    }

    /**
     * 计算距离纪念日的天数
     *
     * @param date
     * @return
     */
    public static long calculationLianAi(String date) {
        SimpleDateFormat simpleDateFormat = get();
        Date startDate = new Date();
        try {
            startDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDatePoor(new Date(), startDate);
    }

    /**
     * 计算距离生日天数（阳历）
     *
     * @param birthday 阳历生日
     * @return
     */
    public static long calculationBirthday(String birthday) {
        SimpleDateFormat simpleDateFormat = get();
        Calendar cToday = Calendar.getInstance();
        Calendar cBirth = Calendar.getInstance();
        Date birth = new Date();
        try {
            birth = simpleDateFormat.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cBirth.setTime(birth);
        cBirth.set(Calendar.YEAR, cToday.get(Calendar.YEAR));
        int days;
        if (cBirth.get(Calendar.DAY_OF_YEAR) < cToday.get(Calendar.DAY_OF_YEAR)) {
            //如果今年的生日已经过了
            //计算距离过完今年还有多少天
            days = cToday.getActualMaximum(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
            //加上明天生日是在明天的第多少天
            days += cBirth.get(Calendar.DAY_OF_YEAR);
        } else {
            days = cBirth.get(Calendar.DAY_OF_YEAR) - cToday.get(Calendar.DAY_OF_YEAR);
        }
        return days;
    }

    /**
     * 计算距离生日天数（阴历）
     *
     * @param birthday 阳历生日  注意:虽然这里计算的是阴历生日,但参数仍然是阳历的生日
     * @return
     */
    public static long calculationBirthdayByLunar(String birthday) {
        Solar solar = new Solar();
        solar.parseDate(birthday);
        // 转农历
        Lunar lunar = LunarSolarConverter.SolarToLunar(solar);
        System.out.println("出生那年的阴历日期:" + lunar);
        // 获取今年生日阳历日期
        lunar.setLunarYear(Calendar.getInstance().getWeekYear());
        solar = LunarSolarConverter.LunarToSolar(lunar);
        System.out.println("今年生日的阳历日期:" + solar);
        return calculationBirthday(solar.getStringDate());
    }

    public static void main(String[] args) {
        System.out.println("阴历生日还有:" + calculationBirthdayByLunar("1998-01-06") + "天");
        System.out.println("阳历生日还有:" + calculationBirthday("1998-01-06") + "天");
    }
}
