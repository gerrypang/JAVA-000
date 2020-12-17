package com.gerry.pang.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Component
public class CustomThreadPoolIgnorePolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        this.saveRunable(runnable, executor);
        this.doLog(runnable, executor);
        this.sendAlarm(runnable, executor);
    }

    private void saveRunable(Runnable runnable, ThreadPoolExecutor executor) {
        log.info(">>>>> saveRunable 拒绝线程落盘开始");
            try {
                log.error("do something");
            } catch (Exception e) {
                log.error("拒绝线程落盘失败", e);
        }
        log.info("<<<<< saveRunable 拒绝线程落盘结束");
    }

    /**
     * 记录日志
     *
     * @param runnable
     * @param executor
     * @author pangguowei
     * @since 2020年4月3日
     */
    private void doLog(Runnable runnable, ThreadPoolExecutor executor) {
        // 可做日志记录等
        log.error("===== 捕获线程拒绝信息 =====");
        log.error("==> runnable: {}", runnable.toString());
        log.error("==> executor: {}", JSONObject.toJSONString(executor));
    }

    /**
     * 线程拒绝策略告警
     *
     * @param runnable
     * @param executor
     */
    private void sendAlarm(Runnable runnable, ThreadPoolExecutor executor) {
        log.info(">>>>> sendAlarm 线程执行拒绝告警开始");
        try {
            log.error("do something");
        } catch (Exception e) {
            log.error("线程执行拒绝告警信息发送失败", e);
        }
        log.info("<<<<< sendAlarm 线程执行拒绝结束");
    }
}
