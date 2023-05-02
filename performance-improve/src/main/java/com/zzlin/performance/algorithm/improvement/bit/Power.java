package com.zzlin.performance.algorithm.improvement.bit;

/**
 * 题目五
 * 判断一个32位正数是不是2的幂、4的幂
 *
 * @author zlin
 * @date 20230502
 */
public class Power {

    public static void main(String[] args) {
        System.out.println(is2Power(12));
        System.out.println(is2Power(1));

        System.out.println(is4Power(16));
        System.out.println(is4Power(-1));
    }


    /**
     * 只有一个1的数就是2的幂
     */
    public static boolean is2Power(int n) {
        return (n & (n - 1)) == 0;
    }

    /**
     * 1、是2的幂=只有一个1
     * 2、与上01010101 01010101 01010101 01010101不为0
     */
    public static boolean is4Power(int n) {
        // 0x55555555 = 01010101 01010101 01010101 01010101
        return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
    }

}
