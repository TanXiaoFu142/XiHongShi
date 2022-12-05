package com.cws.scheduled;

import com.cws.utils.PushUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Task 定时任务
 *
 * @author cws
 * @date 2022/8/22 21:42
 */
@EnableScheduling
@Configuration
public class Task {
    // 定时 早8点推送  0秒 0分 8时
    @Scheduled(cron = "0 0 8 * * ?")
    public void goodMorning() {
        new PushUtil().push();
//         PushUtil.push();
    }
}
