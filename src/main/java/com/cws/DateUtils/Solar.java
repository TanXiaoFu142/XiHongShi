package com.cws.DateUtils;


import lombok.Data;

/**
 * 阳历
 */
@Data
public class Solar {
    public int solarYear;
    public int solarMonth;
    public int solarDay;

    /**
     * 转成String类型的日期
     *
     * @return "yyyy-MM-dd"
     */
    public String getStringDate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.solarYear);
        stringBuilder.append("-");
        if (this.solarMonth < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(this.solarMonth);
        stringBuilder.append("-");
        if (this.solarDay < 10) {
            stringBuilder.append(0);
        }
        stringBuilder.append(this.solarDay);
        return stringBuilder.toString();
    }

    public void parseDate(String birthday) {
        String[] split = birthday.split("-");
        this.solarYear = Integer.parseInt(split[0]);
        this.solarMonth = Integer.parseInt(split[1]);
        this.solarDay = Integer.parseInt(split[2]);
    }

    @Override
    public String toString() {
        return solarYear + "年" + solarMonth + "月" + solarDay + "日";
    }
}

