package com.zzlin.performance.algorithm.improvement.base.hash;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * 设计RandomPool结构
 * [题目]
 * 设计一种结构，在该结构中有如下三个功能:
 * insert(key):将某个key加入到该结构，做到不重复加入
 * delete(key):将原本在结构中的某个key移除
 * getRandom():等概率随机返回结构中的任何一个key
 * [要求]Insert、delete和getRandom方法的时间复杂度都是0(1)
 *
 * @author zlin
 * @date 20230226
 */
public class RandomPoolTest {

    public static void main(String[] args) {
        RandomPool randomPool = new RandomPool();
        randomPool.insert("apple");
        randomPool.insert("apple");
        randomPool.insert("back");
        randomPool.insert("block");
        System.out.println(randomPool.getRandom());
        System.out.println(randomPool.getRandom());
        System.out.println(randomPool.getRandom());
        System.out.println(randomPool.getRandom());
    }

    private static class RandomPool{
        /**
         * 字符串，index
         */
        private Map<String, Integer> keyIndexMap;
        /**
         * index，字符串
         */
        private Map<Integer, String> indexKeyMap;
        /**
         * 当前有多少个不同字符串
         */
        private int size;

        public RandomPool() {
            this.keyIndexMap = new HashMap<>();
            this.indexKeyMap = new HashMap<>();
        }

        public void insert(String key) {
            if (keyIndexMap.containsKey(key)) {
                return;
            }
            keyIndexMap.put(key, this.size);
            indexKeyMap.put(this.size++, key);
        }

        public void delete(String key) {
            Integer keyIndex = keyIndexMap.get(key);
            if (keyIndex == null) {
                return;
            }
            int lastKeyIndex = --this.size;
            String lastKey = indexKeyMap.get(lastKeyIndex);
            keyIndexMap.put(lastKey, keyIndex);
            indexKeyMap.put(keyIndex, lastKey);
            keyIndexMap.remove(key);
            indexKeyMap.remove(lastKeyIndex);
        }

        public String getRandom() {
            return indexKeyMap.get(new Random().nextInt(this.size));
        }
    }

}
