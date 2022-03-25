package com.zzlin.performance.threadpool;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * 计算线程池参数
 * @author zlin
 * @date 20220322
 */
public class MyPoolSizeCalculator extends PoolSizeCalculator {

  public static void main(String[] args) {
    MyPoolSizeCalculator calculator = new MyPoolSizeCalculator();
    calculator.calculateBoundaries(
      // CPU目标利用率
      new BigDecimal(1.0),
      // blockingqueue占用的内存大小，byte
      new BigDecimal(100000)
    );
  }

  @Override
  protected long getCurrentThreadCPUTime() {
    // 当前线程占用的总时间
    return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
  }

  @Override
  protected Runnable creatTask() {
    return new AsynchronousTask();
  }

  @Override
  protected BlockingQueue createWorkQueue() {
    return new LinkedBlockingQueue<>();
  }

}

/**
 * 要计算的运行线程
 */
class AsynchronousTask implements Runnable {

  @Override
  public void run() {
    // 线程池将要执行的业务
    // System.out.println(Thread.currentThread().getName());
  }
}