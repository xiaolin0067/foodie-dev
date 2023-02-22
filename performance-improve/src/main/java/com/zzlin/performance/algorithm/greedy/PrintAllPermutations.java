package com.zzlin.performance.algorithm.greedy;

import java.util.*;

/**
 * 打印一个字符串的全部排列
 * 打印一个字符串的全部排列，要求不要出现重复的排列
 *
 * @author zlin
 * @date 20230222
 */
public class PrintAllPermutations {

    public static void main(String[] args) {
        for (String str : permutations("abcc")) {
            System.out.println(str);
        }
        System.out.println("----------------------------");
        for (String str : permutations("abc")) {
            System.out.println(str);
        }
    }

    private static List<String> permutations(String str) {
        if (Objects.isNull(str) || str.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        char[] chars = str.toCharArray();
        process(chars, 0, result);
        return result;
    }

    /**
     * chars[i~length-1] 范围都可以在i位置上进行尝试
     * chars[0~i-1] 范围上是之前已做的选择
     *
     * @param chars 输入原始字符
     * @param i 当前处理i位置
     * @param result 所有已做过的合法选择
     */
    private static void process(char[] chars, int i, List<String> result) {
        if (i == chars.length) {
            result.add(String.valueOf(chars));
            return;
        }
        Set<Character> charProcessed = new HashSet<>();
        for (int j = i; j < chars.length; j++) {
            if (charProcessed.contains(chars[j])) {
                // 当前位置，当前字符已被处理，不在重复处理
                continue;
            }
            charProcessed.add(chars[j]);
            // 将i后面的每一个位置的字符都处理一遍
            swap(chars, i, j);
            process(chars, i + 1, result);
            // 还原交换的字符
            swap(chars, i, j);
        }
    }

    private static void swap(char[] chars, int index1, int index2) {
        if (chars == null || index1 == index2) {
            return;
        }
        char chr = chars[index1];
        chars[index1] = chars[index2];
        chars[index2] = chr;
    }

}
