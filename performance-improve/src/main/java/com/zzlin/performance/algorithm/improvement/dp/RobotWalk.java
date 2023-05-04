package com.zzlin.performance.algorithm.improvement.dp;

/**
 * 机器人运动问题
 *
 * int n, 一共有n个位置, 正整数大于1：1, 2, 3 ... n
 * int s, start机器人初始位置, 1 <= s <= n
 * int e, end机器人要到的位置
 * int k, 机器人每次必须走k步
 * 机器人可以往左走也可以往右走，从s到e有多少中方法
 *
 * @author zlin
 * @date 20230503
 */
public class RobotWalk {

    public static int walkWays(int n, int s, int e, int k) {
        return f(n, e, k, s);
    }

    /**
     * 递归主函数
     *
     * @param n 一共有n个位置, 正整数大于1：1, 2, 3 ... n   固定参数
     * @param e end机器人要到的位置  固定参数
     * @param rest 机器人当前还剩多少步数可以走
     * @param cur 机器人当前所在位置
     * @return 有多少中方式到达
     */
    private static int f(int n, int e, int rest, int cur) {
        if (rest == 0) {
            return cur == e ? 1 : 0;
        }
        // 若机器人来到了1位置，下一步只能往2走
        if (cur == 1) {
            return f(n, e, rest - 1, 2);
        }
        // 若机器人来到了n位置，下一步只能往n - 1走
        if (cur == n) {
            return f(n, e, rest - 1, n - 1);
        }
        // 机器人在(1, n)中间，两边都可以走
        return f(n, e, rest - 1, cur + 1) + f(n, e, rest - 1, cur - 1);
    }

    /**
     * 记忆化搜索
     */
    public static int walkWays2(int n, int s, int e, int k) {
        int[][] dp = new int[k + 1][n + 1];
        for (int i = 0; i <= k; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }
        return f2(n, e, k, s, dp);
    }

    /**
     * 记忆化搜索
     *
     * @param n 一共有n个位置, 正整数大于1：1, 2, 3 ... n   固定参数
     * @param e end机器人要到的位置  固定参数
     * @param rest 机器人当前还剩多少步数可以走
     * @param cur 机器人当前所在位置
     * @return 有多少中方式到达
     */
    private static int f2(int n, int e, int rest, int cur, int[][] dp) {
        if (dp[rest][cur] == -1) {
            if (rest == 0) {
                dp[rest][cur] = cur == e ? 1 : 0;
            }else if (cur == 1) {
                dp[rest][cur] = f2(n, e, rest - 1, 2, dp);
            }else if (cur == n) {
                dp[rest][cur] = f2(n, e, rest - 1, n - 1, dp);
            }else {
                dp[rest][cur] = f2(n, e, rest - 1, cur + 1, dp)
                        + f2(n, e, rest - 1, cur - 1, dp);
            }
        }
        return dp[rest][cur];
    }

    /**
     * 递归 -> 严格表结构的动态规划
     * 例如：n=5, s=2, e=4, k=4
     * rest\cur | 0 | 1 | 2 | 3 | 4 | 5 |
     * -----------------------------------
     *   0      | X | 0 | 0 | 0 | 1 | 0 |
     *   1      | X | 0 | 0 | 1 | 0 | 1 |
     *   2      | X | 0 | 1 | 0 | 2 | 0 |
     *   3      | X | 1 | 0 | 3 | 0 | 2 |
     *   4      | X | 0 | 4 | 0 | 5 | 0 |
     * 根据递归函数可以得到：
     * 1)rest = 0时,只有4位置是1其余都是0
     * 2)cur = 1时,等于右上角的值
     * 3)cur = 5时,等于左上角的值
     * 4)cur = 中间时,等于左右上角值的和
     * 完善动态规划表
     */
    public static int dpWay(int n, int s, int e, int k) {
        int[][] dp = new int[k + 1][n + 1];
        dp[0][e] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int cur = 1; cur <= n; cur++) {
                if (cur == 1) {
                    dp[rest][cur] = dp[rest - 1][2];
                }else if (cur == n) {
                    dp[rest][cur] = dp[rest - 1][n - 1];
                }else {
                    dp[rest][cur] = dp[rest - 1][cur + 1] + dp[rest - 1][cur - 1];
                }
            }
        }
        return dp[s][k];
    }

}
