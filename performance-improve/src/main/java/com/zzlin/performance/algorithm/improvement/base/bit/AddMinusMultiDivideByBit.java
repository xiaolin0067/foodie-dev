package com.zzlin.performance.algorithm.improvement.base.bit;

/**
 * @author zlin
 * @date 20230502
 */
public class AddMinusMultiDivideByBit {

    public static void main(String[] args) {
        System.out.println(add(7, 15));
        System.out.println(minus(15, 7));
        System.out.println(multi(3, 5));

        System.out.println(div(9, 2));
        System.out.println(div(-9, -3));
    }


    /**
     * 用户需注意溢出场景，Java本身溢出也会错误
     * a ^ b 无进位相加
     * a & b 需要进位的位置，(a & b) << 1，进位的位置
     * 周而复始的计算，进位的位置为0后，无进位相加的结果即为sum
     */
    public static int add(int a, int b) {
        int sum = a;
        while (b != 0) {
            sum = a ^ b;
            b = (a & b) << 1;
            a = sum;
        }
        return sum;
    }

    public static int minus(int a, int b) {
        return add(a, negNum(b));
    }

    /**
     * a     011010
     * b     001100
     *      -------
     * a    000000   b 00110
     * a   000000    b 0011
     * a  011010     b 001
     * a 011010      b 00
     * -------------
     */
    public static int multi(int a, int b) {
        int res = 0;
        while (b != 0) {
            if ((b & 1) != 0) {
                res = add(res, a);
            }
            a <<= 1;
            b >>>= 1;
        }
        return res;
    }

    /**
     * 除法
     */
    public static int div(int a, int b) {
        int x = isNeg(a) ? negNum(a) : a;
        int y = isNeg(b) ? negNum(b) : b;
        int res = 0;
        for (int i = 31; i > -1; i = minus(i, 1)) {
            if ((x >> i) >= y) {
                res |= (1 << i);
                x = minus(x, y << i);
            }
        }
        return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
    }

    /**
     * 取一个数的相反数 = 取反 + 1
     */
    public static int negNum(int n) {
        return add(~n, 1);
    }

    /**
     * 是否为负数
     */
    private static boolean isNeg(int n) {
        return n < 0;
    }

}
