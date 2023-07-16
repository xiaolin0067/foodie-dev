package com.zzlin.performance.algorithm.improvement.dp;

/**
 * 给定数组里代表货币的面值，没有重复值，每一种货币面值可以使用一张或多张
 * 最终要找的零钱数是aim
 * 求找零的方式一共有多少种
 * @author zlin
 * @date 20230716
 */
public class CoinsWay {


    public static int way(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     *
     * @param arr 货币面值，不重复
     * @param index arr[index]及之后的面值可以使用
     * @param rest 还需找零的钱数
     * @return 方法数
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest ==0 ? 1 : 0;
        }
        int ways = 0;
        for (int count = 0; arr[index] * count <= rest; count++) {
            ways += process(arr, index + 1, rest - arr[index] * count);
        }
        return ways;
    }

}
