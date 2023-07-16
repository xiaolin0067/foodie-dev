package com.zzlin.performance.algorithm.improvement.dp;

/**
 * 从(0,0)位置, 去往(x,y)位置, 必须跳step步, 返回跳的方法数
 * @author zlin
 * @date 20230716
 */
public class HorseJump {

    public static void main(String[] args) {
        System.out.println(getWays(7,7,10));
        System.out.println(dpWays(7,7,10));
    }

    public static int getWays(int x, int y, int step) {
        return process(x, y, step);
    }

    private static int process(int x, int y, int step) {
        // 棋盘越界
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        // 已无剩余步数
        if (step == 0) {
            return (x == 0 && y == 0) ? 1 : 0;
        }
        // 可以一步到达x,y位置的8个位置的数量
        return process(x -1, y + 2, step - 1) +
                process(x - 2, y + 1, step - 1) +
                process(x - 2, y - 1, step - 1) +
                process(x - 1, y - 2, step - 1) +
                process(x + 1, y - 2, step - 1) +
                process(x + 2, y - 1, step - 1) +
                process(x + 2, y + 1, step - 1) +
                process(x + 1, y + 2, step - 1);
    }

    private static int dpWays(int x, int y, int step) {
        // 棋盘越界
        if (x < 0 || x > 8 || y < 0 || y > 9 || step < 0) {
            return 0;
        }
        int[][][] dp = new int[9][10][step + 1];
        dp[0][0][0] = 1;
        for (int h = 1; h <= step; h++) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 10; j++) {
                    dp[i][j][h] += getDpValue(dp, i - 1, j + 2, h - 1);
                    dp[i][j][h] += getDpValue(dp, i - 2, j + 1, h - 1);
                    dp[i][j][h] += getDpValue(dp, i - 2, j - 1, h - 1);
                    dp[i][j][h] += getDpValue(dp, i - 1, j - 2, h - 1);
                    dp[i][j][h] += getDpValue(dp, i + 1, j - 2, h - 1);
                    dp[i][j][h] += getDpValue(dp, i + 2, j - 1, h - 1);
                    dp[i][j][h] += getDpValue(dp, i + 2, j + 1, h - 1);
                    dp[i][j][h] += getDpValue(dp, i + 1, j + 2, h - 1);
                }
            }
        }
        return dp[x][y][step];
    }

    private static int getDpValue(int[][][] dp, int x, int y, int step) {
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            return 0;
        }
        return dp[x][y][step];
    }

}
