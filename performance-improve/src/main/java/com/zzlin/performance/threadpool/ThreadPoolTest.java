package com.zzlin.performance.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author zlin
 * @date 20220322
 */
@Slf4j
public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int coreSize = Runtime.getRuntime().availableProcessors() * 2;
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                coreSize,
                coreSize * 2,
                // 默认指的是非核心线程的空闲回收时间，若指定了allowCoreThreadTimeOut(true)，则核心线程也会被回收
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(),
                // defaultThreadFactory (默认）︰创建的线程拥有相同优先级、非守护线程、有线程名称
                // privilegedThreadFactory :在defaultThreadFactory的基础上，
                // 可以让运行在这个线程中的任务拥有和这个线程相同的访问控制和ClassLoader
                Executors.defaultThreadFactory(),
                // AbortPolicy (默认）︰抛异常
                // CallerRusPolicy: 用调用者所在的线程执行任务
                // DiscardOldestPolicy: 丢弃队列中最靠前的任务
                // DiscardPolicy: 丢弃当前任务
                new ThreadPoolExecutor.AbortPolicy()
        );
        threadPool.allowCoreThreadTimeOut(true);
        // execute()︰提交任务，交给线程池执行
        // submit():提交任务，能够返回执行结果

        // shutdown():关闭线程池，等待任务都执行
        // shutdownNow():关闭线程池，不等任务执行完(很少使用)
        // getTaskCount():返回线程池已执行和未执行的任务总数
        // getCompletedTaskCount () ∶已完成的任务数量
        // getPoolSize () ∶线程池当前的线程数量
        // getActiveCount ) :线程池中正在执行任务的线程数量
        Future<Object> submit = threadPool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                log.info("test1...............");
                return "test1 return";
            }
        });
        Object returnObj = submit.get();
        log.info("submit.get(): {}", returnObj);

        threadPool.execute(() -> {
            log.info("test2...............");
        });
        threadPool.shutdown();
    }

}
