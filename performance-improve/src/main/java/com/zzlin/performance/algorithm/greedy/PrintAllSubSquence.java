package com.zzlin.performance.algorithm.greedy;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 打印一个字符串的全部子序列，包括空字符串
 *
 * @author zlin
 * @date 20230222
 */
public class PrintAllSubSquence {

    public static void main(String[] args) {
        printAllSubSquence("abc");
    }

    /**
     * 打印所有子串的，入口方法
     */
    public static void printAllSubSquence(String str) {
        char[] chars = str.toCharArray();
        process(chars, 0);
        System.out.println("------------------------");
        process(chars, 0, new ArrayList<>());
    }

    /**
     * 递归方法
     * 每个字符，要么选择，要么不选择，打印最终选择的字符列表
     *
     * @param chars 输入的字符数组
     * @param i 当前来到的位置
     */
    private static void process(char[] chars, int i) {
        if (i == chars.length) {
            List<Character> list = String.valueOf(chars).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            // base case, 当前走到了末尾位置，打印所有已选择的字符
            System.out.println(list);
            return;
        }
        process(chars, i + 1);
        char tmp = chars[i];
        chars[i] = 0;
        process(chars, i + 1);
        chars[i] = tmp;
    }

    /**
     * 递归方法
     * 每个字符，要么选择，要么不选择，打印最终选择的字符列表
     *
     * @param chars 输入的字符数组
     * @param i 当前来到的位置
     * @param selected 当前选择的所有字符
     */
    private static void process(char[] chars, int i, List<Character> selected) {
        if (i == chars.length) {
            // base case, 当前走到了末尾位置，打印所有已选择的字符
            System.out.println(selected);
            return;
        }
        List<Character> keepChar = Lists.newArrayList(selected);
        keepChar.add(chars[i]);
        // 选择当前字符：把已选择的字符复制一份儿，加上当前字符
        process(chars, i + 1, keepChar);
        // 不选择当前字符：直接复制已选择的字符进行处理
        process(chars, i + 1, Lists.newArrayList(selected));
    }


}
