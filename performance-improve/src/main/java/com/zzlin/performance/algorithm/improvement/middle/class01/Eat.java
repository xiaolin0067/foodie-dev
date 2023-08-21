package com.zzlin.performance.algorithm.improvement.middle.class01;

/**
 * 两只动物轮流吃草，每次只能吃4的幂次个单位的草，假设两个动物都绝顶聪明，给出N个单位的草，请问先手会获胜还是后手
 *
 * @author lin
 * @date 2023/8/20
 */
public class Eat {

    /**
     *
     * @param n 草的数量
     * @return 0 - 后手获胜，1 - 先手获胜
     */
    public static int winner1(int n) {
        /*
        0-后，1-先，2-后，3-先，4-先
         */
        if (n < 5) {
            return (n == 0 || n == 2) ? 0 : 1;
        }
        // 先手吃的数量
        int base = 1;
        while (base <= n) {
            // 先手吃完了之后后手可以吃的草的单位为 n - base
            // 后手返回的获胜者的后手既当前的先手
            if (winner1(n - base) == 0) {
                return 1;
            }
            // 防止base *= 4溢出
            if (base > n / 4) {
                break;
            }
            base *= 4;
        }
        return 0;
    }

    public static int winner2(int n) {
        if (n % 5 == 0 || n % 5 == 2) {
            return 0;
        } else {
            return 1;
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            System.out.println(i + " : " + winner1(i) + " _ " + winner2(i));
        }
    }

}
