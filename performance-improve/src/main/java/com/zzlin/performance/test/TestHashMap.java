package com.zzlin.performance.test;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * @author zlin
 * @date 20220701
 */
public class TestHashMap {

    public static void main(String[] args) {
        Hashtable<String, Object> hashtable = new Hashtable<>();
        hashtable.put("561", new Object());
        hashtable.get("48512");
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(map.size());
        map.put("123", new Object());
        System.out.println(map.size());
        System.out.println(map.get("123"));
    }

}
