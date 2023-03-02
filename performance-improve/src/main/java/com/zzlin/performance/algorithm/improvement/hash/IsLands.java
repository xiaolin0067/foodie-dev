package com.zzlin.performance.algorithm.improvement.hash;

import java.util.Arrays;

/**
 *
 岛问题
 [题目]
 一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右 四个位置相连。
 如果有一片1连在一起，这个部分叫做一个岛，求一个矩阵中有多少个岛?
 [举例]
 001010
 111010
 100100
 000000
 这个矩阵中有三个岛
 [进阶]
 如何设计一个并行算法解决这个问题

 * @author zlin
 * @date 20230302
 */
public class IsLands {

    public static void main(String[] args) {
        int[][] m = {
                {0,0,0,0,1,0,0,0,0,0},
                {0,1,1,1,1,0,0,0,1,1},
                {0,0,0,1,1,1,0,0,0,1},
                {0,0,1,1,0,0,0,1,0,0},
                {0,1,0,0,1,0,0,0,0,0},
                {1,1,0,0,0,0,1,1,0,0},
                {0,1,0,0,0,0,1,0,0,0},
        };
        System.out.println(countLands(m));
        System.out.println(Arrays.deepToString(m));
    }

    private static int countLands(int[][] m) {
        if (m == null || m[0] == null) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int result = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (m[i][j] == 1) {
                    result++;
                    infect(m, row, col, i, j);
                }
            }
        }
        return result;
    }

    private static void infect(int[][] m, int row, int col, int i, int j) {
        if (i < 0 || i >= row || j < 0 || j >= col || m[i][j] != 1) {
            return;
        }
        m[i][j] = 2;
        infect(m, row, col, i + 1, j);
        infect(m, row, col, i - 1, j);
        infect(m, row, col, i, j + 1);
        infect(m, row, col, i, j - 1);
    }

}
