package com.zzlin.performance.algorithm.hot.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 相等行列对
 * <a href="https://leetcode.cn/problems/equal-row-and-column-pairs/"></a>
 *
 * @author pang
 * @date 2024/8/18 12:19
 * @since V3.17.0
 */
public class EqualRowAndColumnPairs {

    public static void main(String[] args) {
        System.out.println(new Solution().equalPairs(new int[][]{{11,1}, {1,11}}));

    }
    static class Solution {
        public int equalPairs(int[][] grid) {
            int n = grid.length;
            if (n < 2) {
                return n;
            }
            Map<String, Integer> rowMap = new HashMap<>();
            for (int[] value : grid) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < n; j++) {
                    sb.append(value[j]).append(",");
                }
                String key = sb.toString();
                int count = rowMap.computeIfAbsent(key, k -> 0);
                rowMap.put(key, count + 1);
            }
            int res = 0;
            for (int i = 0; i < n; i++) {
                StringBuilder sb = new StringBuilder();
                for (int[] ints : grid) {
                    sb.append(ints[i]).append(",");
                }
                String key = sb.toString();
                res += rowMap.computeIfAbsent(key, k -> 0);
            }
            return res;
        }
    }
}
