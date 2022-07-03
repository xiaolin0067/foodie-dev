package com.zzlin.performance.algorithm.base;

/**
 * 1、数组中只有一个数出现了奇数次，找出这个出现奇数次的数
 * 2、数组中有两个数出现了奇数次，找出这两个出现奇数次的数
 * @author zlin
 * @date 20220605
 */
public class OddTimes {

    public static void main(String[] args) {
        int[] arr = new int[]{
                1,1,1,1,
                2,2,
                3
        };
        System.out.println(oneNumInArrayOddTimes(arr));
        int[] arr2 = new int[]{
                1,1,1,1,
                2,2,
                3,
                4
        };
        twoNumInArrayOddTimes(arr2);
    }


    /**
     * 数组中只有一个数出现了奇数次，找出这个出现奇数次的数
     * 0与数组中的每一个数相异或，最后的异或结果就是这一个出现了奇数次的数
     * 0 ^ (a^a^a^a) ^ (b^b) ^ (c^c) ^ ((d^d)^d) = d
     * 0 ^ 0 ^ 0 ^ 0 ^ d = d
     */
    public static int oneNumInArrayOddTimes(int[] arr) {
        int aor = 0;
        for (int num : arr) {
            aor ^= num;
        }
        return aor;
    }

    /**
     * 数组中有两个数出现了奇数次，找出这两个出现奇数次的数
     * 1、0与数组中的每一个数相异或，最后的异或结果是这两个出现奇数次数的异或
     *     0 ^ (a^a^a^a) ^ (b^b) ^ (c^c) ^ ((d^d)^d) ^ ((e^e)^e) = d ^ e
     * 2、d,e两个数不相等 => d^e不等于0, 则d^e的结果必定有一位等于1, 找到d^e结果的最右侧的1的位置记为 rightIndex
     * 3、0与数组中的每一个数和rightIndex相与的值为1的数相异或，得到的结果即 d or e
     *     因为同一种数必定会落在同一边，而d和e不同必定在不同的一边，其他数都出现了偶数次同上直接抵消，故只剩下d or e
     *     同一种数和rightIndex相与的值必定是一样的，而d和e不一样只会留下d or e来进行异或计算
     *     偶数次出现的数被抵消，最后留下的就只有d or e了
     * 4、d or e与d ^ e相异或，得到的就是另外一个值
     */
    public static void twoNumInArrayOddTimes(int[] arr) {
        int aor1 = 0;
        for (int num : arr) {
            aor1 ^= num;
        }
        // aor1 = d ^ e
        int aor2 = 0;
        // 获得最右侧的为1的位置
        int rightIndex = aor1 & (~aor1 + 1);
        for (int num : arr) {
            // 该数在该位置也是1
            if ((num & rightIndex) == 0) {
                aor2 ^= num;
            }
        }
        // aor2 = d or e
        aor1 ^= aor2;
        System.out.println("奇数次出现在数组中的两个数为：" + aor1 + ", " + aor2);
    }

}
