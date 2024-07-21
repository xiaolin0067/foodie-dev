package com.zzlin.performance.algorithm.hot;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a href="https://leetcode.cn/problems/find-the-winner-of-the-circular-game/">约瑟夫问题</a>
 * 最后一人的编号 约瑟夫问题
 *
 * @author pang
 * @date 2024/7/17
 */
public class FindTheWinnerTest {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findTheWinner(5, 2) == 3);
        System.out.println(solution.findTheWinner(6, 5) == 1);
    }

    static class Solution {
        public int findTheWinner(int n, int k) {
            Queue<Integer> queue = new LinkedList<>();
            for (int i = 1; i <= n; i++) {
                queue.add(i);
            }
            int j = k - 1;
            while (queue.size() > 1) {
                for (int i = 0; i < j; i++) {
                    queue.add(queue.poll());
                }
                queue.poll();
            }
            return queue.peek();
        }
    }
}
