package com.zzlin.performance.algorithm.base.greedy;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 *  贪心
 *  分整块金条，怎么分最省铜板
 *  哈夫曼编码问题
 *
 * @author zlin
 * @date 20230206
 */
public class LessMoneySplitGod {

    public static void main(String[] args) {
        System.out.println(lessMoney(new int[]{20,30,10}));
    }


    public static int lessMoney(int[] arr) {
        if (arr == null) {
            return 0;
        }
        PriorityQueue<Integer> priorityQueue = Arrays.stream(arr).boxed().collect(Collectors.toCollection(PriorityQueue::new));
        int result = 0;
        while (priorityQueue.size() > 1) {
            int sum = priorityQueue.poll() + priorityQueue.poll();
            result += sum;
            priorityQueue.add(sum);
        }
        return result;
    }

}
