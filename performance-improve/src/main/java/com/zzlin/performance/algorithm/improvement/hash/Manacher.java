package com.zzlin.performance.algorithm.improvement.hash;

/**
 * 题目
 * Manacher算法解决的问题
 * 字符串str中，最长回文子串的长度如何求解?
 * 如何做到时间复杂度0(N)完成?
 * 虚轴字符使用什么都可以，因为实轴始终和实轴的值相比，虚轴始终和虚轴相比，不会交叉
 *
 *
 * 题目
 * Manacher算法的全部细节和实现讲解
 *
 * Manacher算法不仅只解决最大回文串的问题，还可以解决很多的问题
 *
 * @author zlin
 * @date 20230305
 */
public class Manacher {

    public static int maxLcpLength(String param) {
        if (param == null || param.isEmpty()) {
            return 0;
        }
        char[] str = manacherString(param);
        // 每个位置字符的回文半径数组
        int[] pArr = new int[str.length];
        // 中心对称轴位置
        int c = -1;
        // r为回文右边界+1，即最右的有效区位置是r-1
        int r = -1;
        // 处理串的最大回文半径
        int max = Integer.MAX_VALUE;
        // 对每一个位置都求最大回文半径
        for (int i = 0; i < str.length; i++) {
            /*
             * 1. i 在 r 外，至少不用验自己，i的不用验的半径为1(r为回文右边界+1，这里就是 i>= r就是在r之外)
             * 2. i 在 r 内：
             *   2.1. i' 的左边界在r内, i的不用验的半径为：i'的半径
             *   2.2. i' 的左边界在r外, i的不用验的半径为：r - i
             *   2.3. i' 的左边界在r上, i的不用验的半径为：r - i
             */
            pArr[i] = r > i ? Math.min(pArr[c * 2 - i], r - i) : 1;
            // 当前位置的回文半径还可以扩
            while (i + pArr[i] < str.length && i - pArr[i] > -1) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    // 回文半径外的一个位置前后相同，则回文半径++
                    pArr[i]++;
                }else {
                    break;
                }
            }
            // 若当前位置的回文半径已经超出回文右边界，修改回文右边界和回文中心为当前位置的值
            if (i + pArr[i] > r) {
                r = i + pArr[i];
                c = i;
            }
            max = Math.max(max, pArr[i]);
        }
        // 原串长度=处理串半径 - 1
        // 原串的回文串长度=最大的处理串的回文半径 - 1
        return max - 1;
    }

    /**
     * 1221 => #1#2#2#1#
     *
     * @param param 原始字符串
     * @return 添加虚拟轴的字符数组
     */
    private static char[] manacherString(String param) {
        char[] chars = param.toCharArray();
        char[] res = new char[chars.length * 2 + 1];
        int charsIndex = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : chars[charsIndex++];
        }
        return res;
    }

}
