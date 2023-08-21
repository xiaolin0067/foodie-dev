package com.zzlin.performance.algorithm.improvement.base.bit;

/**
 * 位运算的题目
 * 给定两个有符号32位整数a和b,返回a和b中较大的
 * [要求]
 * 不用做任何比较判断
 *
 * @author zlin
 * @date 20230502
 */
public class GetMaxWithoutCompare {

    public static void main(String[] args) {
        int a = -16;
        int b = 1;
        System.out.println(max1(a, b));
        System.out.println(max2(a, b));
        a = 2147483647;
        b = -2147480000;
        // wrong answer because of overflow
        System.out.println(max1(a, b));
        System.out.println(max2(a, b));
    }

    /**
     * 不使用比较,取两个数中较大的数
     * 存在问题,a-b可能溢出导致结果错误,如：21亿 - (-21亿)
     */
    public static int max1(int a, int b) {
        int c = a - b;
        int scA = sign(c);
        int scB = flip(scA);
        // scA 和 scB其中一个为0,这里就会返回a或者b
        return a * scA + b * scB;
    }

    public static int max2(int a, int b) {
        int c = a - b;
        int sa = sign(a);
        int sb = sign(b);
        int sc = sign(c);
        int diffSab = sa ^ sb;
        int sameSab = flip(diffSab);
        /*
         * a为较大值的两种情况：
         *   1、a和b符号位不相同并且a >= 0,符号位不同,可能溢出,但a>=0那么b<0,返回a
         *   2、a和b符号位相同并且a - b >= 0,符号位相同,不会溢出,判断减法结果
         */
        int returnA = diffSab * sa + sameSab * sc;
        int returnB = flip(returnA);
        return a * returnA + b * returnB;
    }


    /**
     * n只能为0或者1
     * 该方法用于将n：
     * 1 -> 0
     * 0 -> 1
     *
     * @param n 只能为0或者1
     * @return 将n进行0 1互换
     */
    private static int flip(int n) {
        return n ^ 1;
    }

    /**
     * 取n的符号位,若n为非负数返回1,为负数则返回0
     *
     * @param n 任意数
     * @return 0/1
     */
    private static int sign(int n) {
        return flip((n >> 31) & 1);
    }


}
