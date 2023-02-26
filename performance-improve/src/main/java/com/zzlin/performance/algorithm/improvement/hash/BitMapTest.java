package com.zzlin.performance.algorithm.improvement.hash;

/**
 * 位图
 *
 * @author zlin
 * @date 20230226
 */
public class BitMapTest {

    private static class BitMap {

        /**
         * 能表示的位数
         * 32 bit * 10 = 320bit
         */
        private int[] value = new int[10];

        /**
         * 拿到该位的值，0或1
         */
        public int getBitValue(int bit) {
            int numIndex = bit / 32;
            int bitIndex = bit % 32;
            return ((value[numIndex] >> bitIndex) & 1);
        }

        /**
         * 设置该位值为0
         */
        public void setBitZero(int bit) {
            int numIndex = bit / 32;
            int bitIndex = bit % 32;
            value[numIndex] = value[numIndex] & (~(1 << bitIndex));
        }

        /**
         * 设置该位值为1
         */
        public void setBitOne(int bit) {
            int numIndex = bit / 32;
            int bitIndex = bit % 32;
            value[numIndex] = value[numIndex] | (1 << bitIndex);
        }

    }

}
