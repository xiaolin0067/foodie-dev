package com.zzlin.performance.stack;

/**
 * 默认配置：20711
 * -Xss144k：1744
 * @author zlin
 * @date 20220415
 */
public class StackOomTest1 {
    private int stackLength = 1;

    private void stackLeak() {
        stackLength++;
        this.stackLeak();
    }

    public static void main(String[] args) {
        StackOomTest1 oom = new StackOomTest1();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }
    }
}
