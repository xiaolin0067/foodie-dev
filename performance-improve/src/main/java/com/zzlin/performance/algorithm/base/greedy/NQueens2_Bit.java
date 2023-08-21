package com.zzlin.performance.algorithm.base.greedy;

/**
 * 使用位运算提升N皇后问题常数时间
 *
 * @author zlin
 * @date 20230217
 */
public class NQueens2_Bit {

    public static void main(String[] args) {
        System.out.println(nQueensBit(1));
        System.out.println(nQueensBit(8));
        // 365596
        System.out.println(nQueensBit(14));
    }

    public static int nQueensBit(int n) {
        // 不支持大于32皇后问题，可以提升int为long类型以提升支持的皇后数
        if (n < 1 || n > 32) {
            return 0;
        }
        // 皇后可以在的位置，1代表可以放皇后，0代表不能放皇后
        int limit = n == 32  ? -1 : (1 << n) - 1;
        return doNQueensBit(limit, 0, 0, 0);
    }

    /**
     * @param limit 当前皇后数位置限制，1-可以皇后，0-不可放皇后
     *              注意以下参数的0、1是否可填皇后发生交换
     * @param colLimit 列位置限制位，1-不可放皇后，0-可以放皇后
     * @param leftDiaLimit 左斜线位置限制位，1-不可放皇后，0-可以放皇后
     * @param rightDiaLimit 右斜线位置限制位，1-不可放皇后，0-可以放皇后
     * @return 皇后位置摆法数
     */
    private static int doNQueensBit(int limit, int colLimit, int leftDiaLimit, int rightDiaLimit) {
        if (limit == colLimit) {
            // 若当前列限制等于了所有位的限制，说明已经完成了一种N皇后的摆法
            return 1;
        }
        // 当前所有可以放皇后的位置，1-可以皇后，0-不可放皇后
        int pos = limit & (~(colLimit | rightDiaLimit | leftDiaLimit));
        int mostRightBit;
        int result = 0;
        while (pos != 0) {
            // 取可选皇后位置的最右一位
            mostRightBit = pos & (~pos + 1);
            pos = pos - mostRightBit;
            result += doNQueensBit(
                    limit,
                    colLimit | mostRightBit,
                    (leftDiaLimit | mostRightBit) << 1,
                    (rightDiaLimit | mostRightBit) >>> 1);
        }
        return result;
    }

}
