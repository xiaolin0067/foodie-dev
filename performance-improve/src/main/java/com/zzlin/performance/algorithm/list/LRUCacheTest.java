package com.zzlin.performance.algorithm.list;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author pang
 * @date 2024/6/29
 */
public class LRUCacheTest {

    public static void main(String[] args) {
        LRUCache2<String, Integer> lruCache = new LRUCache2<>(3);
        lruCache.put("1", 1);
        lruCache.put("2", 2);
        lruCache.put("3", 3);
        System.out.println(lruCache.get("1"));
        System.out.println(lruCache.get("2"));
        lruCache.put("4", 4);
        System.out.println(lruCache.get("1"));
        System.out.println(lruCache.get("3"));
        System.out.println(lruCache.get("4"));
    }

    static class LRUCache<K, V> {
        private final int capacity;
        private final Map<K, V> cache;
        private final LinkedList<K> list;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.cache = new HashMap<>(capacity);
            this.list = new LinkedList<>();
        }

        public synchronized V get(K key) {
            if (!cache.containsKey(key)) {
                return null;
            }
            // 访问这个key直接放到最后，代表最近访问
            list.remove(key);
            list.addLast(key);
            // 然后返回这个key的value
            return cache.get(key);
        }

        public synchronized void put(K key, V value) {
            if (cache.containsKey(key)) {
                list.remove(key);
            }
            // 然后放入缓存并放到最新的一个位置
            cache.put(key, value);
            list.addLast(key);
            // 如果cache满了，就把最久未访问的key删掉
            while (cache.size() > capacity) {
                K remove = list.removeFirst();
                cache.remove(remove);
            }
        }
    }

    static class LRUCache2<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LRUCache2(int capacity) {
            // 初始化容量，开启访问排序
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            // 超过容量，删除最久未使用的元素
            return size() > capacity;
        }
    }
}
