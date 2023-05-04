package com.zzlin.performance.algorithm.improvement.dp;

import java.util.Random;

/**
 *
 * 给定一个数组，数组中地阿扁每一枚硬币的金额
 * 给定一个目标金额
 * 求数组中能拼凑出目标金额的最少硬币数量
 *
 * @author zlin
 * @date 20230503
 */
public class CoinsMin {

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int times = 10000;
        Random random = new Random();
        for (int i = 0; i < times; i++) {
            int[] arr = generateRandomArray(len, max);
            int aim = random.nextInt(max) * 3 + max;
            if (minCoins1(arr, aim) != minCoins2(arr, aim)
                    && minCoins2(arr, aim) != minCoins3(arr, aim)) {
                System.out.println("结果不一致！！！！！！！");
                break;
            }
        }
        System.out.println("结果一致，正常退出！");
    }

    private static int[] generateRandomArray(int len, int max) {
        Random random = new Random();
        int[] arr = new int[random.nextInt(len)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(max);
        }
        return arr;
    }


    private static int minCoins1(int[] arr, int aim) {
        return f1(arr, aim, 0);
    }

    /**
     * @param arr   所有硬币金额原始集合 不变参数
     * @param rest  当前剩余的目标金额
     * @param index 可以选择index位置之后的硬币
     * @return 组成目标金额的硬币数
     */
    private static int f1(int[] arr, int rest, int index) {
        if (rest < 0) {
            return -1;
        }
        if (rest == 0) {
            return 0;
        }
        if (index == arr.length) {
            return -1;
        }
        int p1 = f1(arr, rest, index + 1);
        int p2Next = f1(arr, rest - arr[index], index + 1);
        if (p1 == -1 && p2Next == -1) {
            return -1;
        }
        if (p1 == -1) {
            return p2Next + 1;
        }
        if (p2Next == -1) {
            return p1;
        }
        return Math.min(p1, p2Next + 1);
    }


    /**
     * 记忆化搜索
     */
    private static int minCoins2(int[] arr, int aim) {
        int[][] dp = new int[arr.length + 1][aim + 1];
        for (int i = 0; i <= arr.length; i++) {
            for (int j = 0; j <= aim; j++) {
                dp[i][j] = -2;
            }
        }
        return f2(arr, aim, 0, dp);
    }

    /**
     * 记忆化搜索
     *
     * @param arr   所有硬币金额原始集合 不变参数
     * @param rest  当前剩余的目标金额
     * @param index 可以选择index位置之后的硬币
     * @return 组成目标金额的硬币数
     */
    private static int f2(int[] arr, int rest, int index, int[][] dp) {
        if (rest < 0) {
            return -1;
        }
        if (dp[index][rest] == -2) {
            if (rest == 0) {
                dp[index][rest] = 0;
            } else if (index == arr.length) {
                dp[index][rest] = -1;
            } else {
                int p1 = f2(arr, rest, index + 1, dp);
                int p2Next = f2(arr, rest - arr[index], index + 1, dp);
                if (p1 == -1 && p2Next == -1) {
                    dp[index][rest] = -1;
                } else if (p1 == -1) {
                    dp[index][rest] = p2Next + 1;
                } else if (p2Next == -1) {
                    dp[index][rest] = p1;
                } else {
                    dp[index][rest] = Math.min(p1, p2Next + 1);
                }
            }
        }
        return dp[index][rest];
    }

    /**
     * 严格表依赖
     * int[] arr = {2,3,5,7,2}
     * int aim = 7
     * <p>
     * index\rest | 0 | 1  | 2  | 3  | 4  | 5  | 6  | 7  |
     * ----------------------------------------------------
     * 0  | 0 |    |    |    |    |    |    |    |
     * 1  | 0 |    |    |    |    |    |    |    |
     * 2  | 0 |    |    |    |    |    |    |    |
     * 3  | 0 |    |    |    |    |    |    |    |
     * 4  | 0 |    |    |    |    |    |    |    |
     * 5  | 0 | -1 | -1 | -1 | -1 | -1 | -1 | -1 |
     * 1、每个格子依赖于他下方的格子和下方格子左边的格子
     * 所以从左下角开始填数据
     */
    private static int minCoins3(int[] arr, int aim) {
        int[][] dp = new int[arr.length + 1][aim + 1];
        for (int row = 0; row <= arr.length; row++) {
            dp[row][0] = 0;
        }
        for (int col = 1; col <= aim; col++) {
            dp[arr.length][col] = -1;
        }
        for (int index = arr.length - 1; index >= 0; index--) {
            for (int rest = 1; rest <= aim; rest++) {
                int p1 = dp[index + 1][rest];
                int p2Next = -1;
                if (dp[rest - arr[index]][index + 1] >= 0) {
                    p2Next = dp[index + 1][rest - arr[index]];
                }
                if (p1 == -1 && p2Next == -1) {
                    dp[index][rest] = -1;
                }else if (p1 == -1) {
                    dp[index][rest] = p2Next + 1;
                }else if (p2Next == -1) {
                    dp[index][rest] = p1;
                }else {
                    dp[index][rest] = Math.min(p1, p2Next + 1);
                }
            }
        }
        return dp[0][aim];
    }

}
