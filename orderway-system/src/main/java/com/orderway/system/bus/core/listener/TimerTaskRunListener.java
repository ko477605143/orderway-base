/*
project create by  orderway  and time 20220909
 */
package com.orderway.system.bus.core.listener;

import cn.hutool.cron.CronUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.orderway.system.bus.modular.timer.entity.SysTimers;
import com.orderway.system.bus.modular.timer.enums.TimerJobStatusEnum;
import com.orderway.system.bus.modular.timer.param.SysTimersParam;
import com.orderway.system.bus.modular.timer.service.SysTimersService;
import com.orderway.system.bus.modular.timer.service.TimerExeService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 项目定时任务启动的listener
 *
 * @author oderway
 * @date 2020/7/1 15:30
 */
public class TimerTaskRunListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        SysTimersService sysTimersService = SpringUtil.getBean(SysTimersService.class);
        TimerExeService timerExeService = SpringUtil.getBean(TimerExeService.class);

        // 获取所有开启状态的任务
        SysTimersParam sysTimersParam = new SysTimersParam();
        sysTimersParam.setJobStatus(TimerJobStatusEnum.RUNNING.getCode());
        List<SysTimers> list = sysTimersService.list(sysTimersParam);

        // 添加定时任务到调度器
        for (SysTimers sysTimers : list) {
            timerExeService.startTimer(String.valueOf(sysTimers.getId()), sysTimers.getCron(), sysTimers.getActionClass());
        }

        // 设置秒级别的启用
        CronUtil.setMatchSecond(true);

        // 启动定时器执行器
        CronUtil.start();
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}
