package com.zzlin.performance.algorithm.improvement.dp;

/**
 * @author zlin
 * @date 20230716
 */
public class BobDie {

    public static void main(String[] args) {
        int N = 10;
        int M = 10;
        int row = 3;
        int col = 2;
        int rest = 5;
        System.out.println(process(N, M, row, col, rest));
        System.out.println(bobLiveRate(N, M, row, col, rest));
    }

    /**
     * 限定Bob在N,M区域内移动, 从row, col位置出发, 需要移动rest步, 若越界则会死亡, 求获取生存移动的方式数量
     *
     * @param N    限定区域
     * @param M    限定区域
     * @param row  起始位置
     * @param col  起始位置
     * @param rest 剩余移动步数
     * @return 活下来的方法数量
     */
    public static int process(int N, int M, int row, int col, int rest) {
        // 越界
        if (row < 0 || row >= N || col < 0 || col >= M) {
            return 0;
        }
        // 没越界，但是剩余步数为0了，代表找到了一种活法
        if (rest == 0) {
            return 1;
        }
        return process(N, M, row - 1, col, rest - 1) +
                process(N, M, row, col - 1, rest - 1) +
                process(N, M, row + 1, col, rest - 1) +
                process(N, M, row, col + 1, rest - 1);
    }

    /**
     * 限定Bob在N,M区域内移动, 从row, col位置出发, 需要移动rest步, 若越界则会死亡, 求Bob的生存率
     */
    public static String bobLiveRate(int N, int M, int row, int col, int rest) {
        int total = (int) Math.pow(4, rest);
        int live = process(N, M, row, col, rest);
        int gcd = gcd(total, live);
        return (live / gcd) + "/" + (total / gcd);
    }


    /**
     * 求两个数的最大公约数
     */
    private static int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }

}
