package com.cws.DateUtils;

import lombok.Data;

/**
 * 阴历
 */
@Data
public class Lunar {
    public int lunarYear;
    public int lunarMonth;
    public int lunarDay;
    public boolean isLeap;

    @Override
    public String toString() {
        return lunarYear + "年" + lunarMonth + "月初" + lunarDay;
    }
}