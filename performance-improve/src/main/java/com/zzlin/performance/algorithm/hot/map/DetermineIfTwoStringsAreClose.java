package com.zzlin.performance.algorithm.hot.map;

import java.util.Arrays;

/**
 * 确定两个字符串是否接近
 * <a href="https://leetcode.cn/problems/determine-if-two-strings-are-close/"></a>
 *
 * @author pang
 * @date 2024/8/18 12:49
 * @since V3.17.0
 */
public class DetermineIfTwoStringsAreClose {

    public static void main(String[] args) {
        System.out.println(new Solution().closeStrings("cabbba", "abbccc"));
    }

    static class Solution {
        // 操作1: 字符串的字符集合一样
        // 操作2: 字符集合的频率一样
        public boolean closeStrings(String word1, String word2) {
            if (word1.length() != word2.length()) {
                return false;
            }
            char[] charArry1 = word1.toCharArray();
            char[] charArry2 = word2.toCharArray();
            int[] n1 = new int[26];
            int[] n2 = new int[26];
            // 计算字符频率和集合
            for (int i = 0; i < charArry1.length; i++) {
                n1[charArry1[i] - 'a']++;
                n2[charArry2[i] - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                // 字符集合不一致
                if ((n1[i] == 0 && n2[i] != 0) || (n1[i] != 0 && n2[i] == 0)) {
                    return false;
                }
            }
            Arrays.sort(n1);
            Arrays.sort(n2);
            // 判断字符频率是否一致
            return Arrays.equals(n1, n2);
        }
    }

}
