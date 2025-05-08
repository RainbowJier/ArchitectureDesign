package com.example.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description：线程池配置
 * @Author： RainbowJier
 * @Data： 2024/9/13 20:14
 */

@Configuration
@EnableAsync
public class ThreadPoolTaskConfig {
    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // 核心线程数
        executor.setCorePoolSize(16);

        // 线程池最大线程数
        executor.setMaxPoolSize(64);

        // 阻塞队列容量
        executor.setQueueCapacity(1024);

        // 非核心线程的空闲存活时间，超过这个值，线程会被销毁。
        // 如果allowCoreThreadTimeOut设置为true，则核心线程也会被销毁。
        executor.setKeepAliveSeconds(30);
        executor.setThreadNamePrefix("自定义线程池-");  // 线程名前缀

        // 拒绝策略，当线程池饱和时，如何处理新任务。
        // CallerRunsPolicy()：交由调⽤方线程运⾏，⽐如main线程；如果添加到线程池失败，那么主线程会⾃⼰去执⾏该任务，不会等待线程池中的线程去执⾏
        //AbortPolicy()：该策略是线程池的默认策略，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
        //DiscardPolicy()：如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常
        //DiscardOldestPolicy()：丢弃队列中最⽼的任务，队列满了，会将最早进⼊队列的任务删掉腾出空间，再尝试加⼊队列
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
