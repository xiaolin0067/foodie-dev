package com.zzlin.performance.algorithm.base.greedy;

/**
 * 汉诺塔问题
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 *
 * @author zlin
 * @date 20230221
 */
public class Hanoi {

    public static void main(String[] args) {
        hanoi(2);
        System.out.println("-----------------------------");
        hanoi(3);
    }

    private static void hanoi(int n) {
        if (n < 0) {
            return;
        }
        func(n, "左", "右", "中");
    }

    /**
     *  1  |  |
     *  |  |  |
     *  |  |  |
     *  v  |  |
     *  i  |  |
     * 整体思路：1、将1 ~ i-1从左移动到中
     *         2、将i从左移动到右
     *         3、将1 ~ i-1从中移动到右
     *
     * @param i 当前圆盘
     * @param start 原始杆
     * @param end 目标杆
     * @param other 其他杆
     */
    private static void func(int i, String start, String end, String other) {
        if (i == 1) {
            // base case 最上面的圆盘，直接移动到目标杆儿
            System.out.println("Move 1 from " + start + " to " + end);
        }else {
            func(i - 1, start, other, end);
            System.out.println("Move " + i + " from " + start + " to " + end);
            func(i - 1, other, end, start);
        }
    }

}
