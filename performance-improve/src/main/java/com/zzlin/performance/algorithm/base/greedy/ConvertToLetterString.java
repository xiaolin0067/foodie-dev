package com.zzlin.performance.algorithm.base.greedy;

/**
 * 规定1和A对应、2和B对应、3和C对应...
 * 那么一个数字字符串比如”111”，就可以转化为"AAA”、"KA"和"AK"
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果
 *
 * @author zlin
 * @date 20230225
 */
public class ConvertToLetterString {

    public static void main(String[] args) {
        System.out.println(number("111"));
    }

    private static int number(String str) {
        return process(str.toCharArray(), 0);
    }

    /**
     * chars[0~i] 已经做了有效的决定
     * chars[i+1 ~ length-1] 待决定
     *
     * @param chars 原始字符
     * @param i 当前位置
     * @return 有多少种转货结果
     */
    private static int process(char[] chars, int i) {
        if (chars.length == i) {
            // 到达末尾，之前做的有效决定到达末尾，有效决定数加1
            return 1;
        }
        if (chars[i] == '0') {
            // 到达当前位置为0，导致当前位置无法做出任何决定，之前做的决定有问题，所以整个决定无效返回0
            return 0;
        }
        if (chars[i] == '1') {
            // 当前'1'单独做一个决定的结果数
            int res = process(chars, i + 1);
            // '1'和后面一个位置合并作为一个决定，如11、12~19，只要后面一个位置存在即可这样做决定
            if (i + 1 < chars.length) {
                res += process(chars, i + 2);
            }
            return res;
        }
        if (chars[i] == '2') {
            // 当前'2'单独做一个决定的结果数
            int res = process(chars, i + 1);
            // '2'和后面一个位置合并作为一个决定，如21、22，最多是26（26个字母）
            if (i + 1 < chars.length && chars[i + 1] <= '6') {
                res += process(chars, i + 2);
            }
            return res;
        }
        // 当前位置为'3' ~ '9'，只能单独一个位置作为一个决定，不可前后合并做决定
        return process(chars, i + 1);
    }

}
