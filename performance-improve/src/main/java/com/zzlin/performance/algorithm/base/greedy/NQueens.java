package com.zzlin.performance.algorithm.base.greedy;

/**
 * N皇后问题
 *
 * N皇后问题是指在N*N的棋盘上要摆N个皇后，要求任何两个皇后不同行、不同列也不在同一条斜线上。
 * 给定一个整数n，返回n皇后的摆法有多少种。
 * n=1，返回1。
 * n=2或3，2皇后和3皇后问题无论怎么摆都不行，返回0
 * n=8，返回92
 *
 * @author zlin
 * @date 20230207
 */
public class NQueens {

    public static void main(String[] args) {
        System.out.println(nQueens(1));
        System.out.println(nQueens(8));
        // 365596
        System.out.println(nQueens(14));
    }

    public static int nQueens(int n) {
        if (n < 0) {
            return 0;
        }
        // 存储第i(0~n-1)行皇后的有效位置(第几列)
        int[] record = new int[n];
        return doNQueens(0, record);
    }

    /**
     *
     * @param i 当前行
     * @param record i-1行皇后的有效位置(第几列)
     * @return 有多少种有效摆法
     */
    private static int doNQueens(int i, int[] record) {
        if (i >= record.length) {
            // record里面都是有效的位置，并且到达了最后一行，说明这是一个有效的摆法，返回1
            return 1;
        }
        int res = 0;
        for (int j = 0; j < record.length; j++) {
            if (isValid(record, i, j)) {
                // 当前位置(当前行列)满足N皇后摆法，继续找下一列的位置，直到找到最终行返回1种摆法，就在结果res加上这一种摆法
                // 深度优先暴力递归
                record[i] = j;
                res += doNQueens(i + 1, record);
            }
        }
        return res;
    }

    /**
     * 当前点(行列)在之前的点记录中是否合法
     *
     * @param record i-1行皇后的有效位置(第几列)
     * @param i 待判断是否合法的点的行
     * @param j 待判断是否合法的点的列
     * @return 当前点位置是否有效
     */
    private static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            /*
            1、i行肯定不和之前的冲突，因为record存储的是i-1行的列值，所以不用判断行，只需要判断列和是否是对角线即可
            2、record[k] == j，若之前的列记录和当前的j列相等，则不合法
            3、Math.abs(i - k) == Math.abs(j - record[k])，若行列值差的绝对值相等的话，说明为一条斜线上
             */
            if (record[k] == j || Math.abs(i - k) == Math.abs(j - record[k])) {
                return false;
            }
        }
        return true;
    }

}
