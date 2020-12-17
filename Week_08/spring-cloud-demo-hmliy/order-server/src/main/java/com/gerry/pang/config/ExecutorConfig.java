package com.gerry.pang.config;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 渠道业务相关线程池统一维护配置类
 * 
 * @author pangguowei
 * @since 2019-11-22
 */
@Configuration
@EnableAsync
@Slf4j
public class ExecutorConfig implements AsyncConfigurer {
    
    @Value("${async.executor.thread.queue-capacity}")
    private int queueCapacity;

    @Value("${async.executor.thread.keep-alive-seconds}")
    private int keepAliveSeconds;
    
    @Value("${async.executor.thread.name-prefix}")
    private String namePrefix;
    
    @Autowired
    private CustomThreadPoolIgnorePolicy ignorePolicy;
    
    @Override
    @Bean(name = "customTaskExecutor")
    @Primary
	public Executor getAsyncExecutor() {
    	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 计算出cpu核心数，以便确定开启的线程数
    	int corePoolSize = Runtime.getRuntime().availableProcessors(); 
    	if (corePoolSize == 1) {
    		corePoolSize *= 4; 
    	}
    	log.info("===> corePoolSize:{} ", corePoolSize);
    	int maxPoolSize = 2 * corePoolSize; 
    	// 核心线程池大小
    	executor.setCorePoolSize(corePoolSize);
    	// 最大线程数
    	executor.setMaxPoolSize(maxPoolSize);
    	// 队列容量
    	executor.setQueueCapacity(queueCapacity);
    	// 活跃时间
    	executor.setKeepAliveSeconds(keepAliveSeconds);
    	// 线程名字前缀
    	executor.setThreadNamePrefix(namePrefix);
		// 等待任务在关机时完成--表明等待所有线程执行完
    	executor.setWaitForTasksToCompleteOnShutdown(true);
    	// 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
    	executor.setAwaitTerminationSeconds(60 * 3);
    	executor.setThreadFactory(new ThreadFactory() {
    		
    		private final AtomicInteger threadCount = new AtomicInteger(0);
    		
			@Override
			public Thread newThread(Runnable runnable) {
				Thread thread = new Thread(runnable);
				thread.setName(namePrefix + "-" + this.threadCount.incrementAndGet());
				if (thread.isDaemon()) {
					thread.setDaemon(false);
				}
				if (Thread.NORM_PRIORITY != thread.getPriority()) {
					thread.setPriority(Thread.NORM_PRIORITY);
				}
				return thread;
			}
    		
    	});
    	/**
    	 * setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
    	 * Reject策略预定义有四种： 
    	 * ThreadPoolExecutor.AbortPolicy策略，是默认的策略。处理程序遭到拒绝将抛出运行时 RejectedExecutionException。 
    	 * ThreadPoolExecutor.CallerRunsPolicy策略，调用者的线程会执行该任务，如果执行器已关闭,则丢弃。 
    	 * ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃。 
    	 * ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）。
    	 */
    	log.info("==> thread ingnore policy:{}", JSONObject.toJSONString(ignorePolicy));
    	executor.setRejectedExecutionHandler(ignorePolicy);
    	executor.initialize();
    	return executor;
	}
    
	/**
	 * 异步任务中异常处理
	 * 
	 * @return
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {
			
			/**
			 * 手动处理捕获的异常
			 */
			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				log.error("===== 捕获线程异常信息 =====");
				log.error("==> Exception Message:{}", JSONObject.toJSONString(ex));
				log.error("==> Exception Method name:{}", method.getName());
				for (Object param : params) {
	                log.info("==> Excetpion Parameter value:{}", JSONObject.toJSONString(param));
	            }
			}
		};
	}
    
}
