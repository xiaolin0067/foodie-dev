package com.zzlin.performance.algorithm.improvement.hash;

/**
 * KMP：字符串str1和str2，str1是否包含str2，如果包含返回str2在str1中开始的位置如何做到时间复杂度0(N)完成?
 *
 * @author zlin
 * @date 20230305
 */
public class KMP {

    /**
     * 字符串str1和str2，str1是否包含str2，如果包含返回str2在str1中开始的位置如何做到时间复杂度0(N)完成?
     * 在 txt 中查找子串 pat，如果存在，返回这个子串的起始索引，否则返回 -1
     *
     * @param txt 文本串
     * @param pad 模式串
     * @return pad在txt字符串中的第一个起始索引
     */
    public static int getIndexOf(String txt, String pad) {
        if (txt == null || pad == null || pad.isEmpty() || txt.length() < pad.length()) {
            return -1;
        }
        char[] str1 = txt.toCharArray();
        char[] str2 = pad.toCharArray();
        int i1 = 0;
        int i2 = 0;
        int[] next = getNextArray(str2);
        // O(2N) = O(N)
        while (i1 < str1.length && i2 < str2.length) {
            if (str1[i1] == str2[i2]) {
                // 两个位置比对通过，两个指针都向后移动
                i1++;
                i2++;
            } else if (next[i2] >= 0) {
                //
                i2 = next[i2];
            } else {
                // i2已经到达0位置了，只能i1++继续往后比对
                i1++;
            }
        }
        return i2 == str2.length ? i1 - i2 : -1;
    }

    /**
     * next[]: str2每个位置之前字符的前缀后缀相同字符数
     * 例如：
     * a, l, a, b
     *
     * next[0] = -1
     * next[1] = 0
     * next[2] = 0
     * next[3] = 1
     *
     * @param chars 原始字符
     * @return 原始字符的每个位置之前字符的前缀后缀相同字符数
     */
    private static int[] getNextArray(char[] chars) {
        if (chars.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[chars.length];
        next[0] = -1;
        next[1] = 0;
        // 求2位置及之后的位置的前后缀相同字符数
        int i = 2;
        // 和 (i- 1) 位置比对的字符的位置，也是(i - 1)的前后缀相同字符数
        int cn = 0;
        while (i < chars.length) {
            if (chars[i - 1] == chars[cn]) {
                /*
                 * i位置的值找到，然后自增循环处理下一个位置
                 * cn是i-1的值，字符相等时即i位置的值为cn+1
                 * i + 1位置需要用到i位置的cn值
                 */
                next[i++] = ++cn;
            } else if (cn > 0) {
                // i - 1 位置字符和cn位置字符不一致，并且cn还可以往前跳
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

}
