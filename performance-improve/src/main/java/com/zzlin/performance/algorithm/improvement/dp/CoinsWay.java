package com.zzlin.performance.algorithm.improvement.dp;

import java.util.Random;

/**
 * 给定数组里代表货币的面值，没有重复值，每一种货币面值可以使用一张或多张
 * 最终要找的零钱数是aim
 * 求找零的方式一共有多少种
 * @author zlin
 * @date 20230716
 */
public class CoinsWay {


    public static int way(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    private static int way2(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 当前index位置的货币面值使用count张[0, n]时的每种情况下能够找零的次数
                for (int count = 0; arr[index] * count <= rest; count++) {
                    dp[index][rest] += dp[index + 1][rest - arr[index] * count];
                }
            }
        }

        return dp[0][aim];
    }

    /**
     * 斜率优化版本，与题意无关，完全由dp表依赖关系规推导得出，没有题意上的含义
     * 当位置依赖为可枚举的情况时可以考虑此优化
     */
    private static int way3(int[] arr, int aim) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;

        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 填依赖的位置为可枚举时，可以考虑斜率优化，此处只依赖与自己下面的位置和自己选用一次后的位置
                dp[index][rest] = dp[index+1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }

        return dp[0][aim];
    }

    /**
     *
     * @param arr 货币面值，不重复
     * @param index arr[index]及之后的面值可以使用
     * @param rest 还需找零的钱数
     * @return 方法数
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest ==0 ? 1 : 0;
        }
        int ways = 0;
        // 当前index位置的货币面值使用count张[0, n]时的每种情况下能够找零的次数
        for (int count = 0; arr[index] * count <= rest; count++) {
            ways += process(arr, index + 1, rest - arr[index] * count);
        }
        return ways;
    }

    private static int[] generateRandomArray(int len, int max) {
        int[] arr = new int[(int)(Math.random() * len) + 1];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * max) + 1;
        }
        return arr;
    }

    public static void main(String[] args) {
        System.out.println("hi");
        int len = 10;
        int max = 10;
        int times = 10000;
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(len, max);
            int aim = random.nextInt(max) * 3 + max;
            if (way(arr, aim) != way2(arr, aim) || way2(arr, aim) != way3(arr, aim)) {
                System.out.println("结果不一致！！！！！！！");
                break;
            }
        }
        System.out.println("结果一致，正常退出！");
    }

}
