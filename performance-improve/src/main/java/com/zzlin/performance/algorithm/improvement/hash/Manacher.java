package com.zzlin.performance.algorithm.improvement.hash;

/**
 * @author zlin
 * @date 20230305
 */
public class Manacher {

    public static int test(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] str = manacherString(s);
        // 每个位置字符的回文半径数组
        int[] pArr = new int[str.length];
        // 中心对称轴位置
        int c = -1;
        // r为回文右边界+1，即最右的有效区位置是r-1
        int r = -1;
        // 结果值，最大回文长度
        int max = Integer.MAX_VALUE;
        // 对每一个位置都求最大回文半径
        for (int i = 0; i < str.length; i++) {
            pArr[i] = r > i ? Math.min(pArr[c * 2 - i], r - i) : 1;
        }
        return max;
    }

    /**
     * 1221 => #1#2#2#1#
     *
     * @param s 原始字符串
     * @return 添加虚拟轴的字符数组
     */
    private static char[] manacherString(String s) {

        return new char[0];
    }

}
