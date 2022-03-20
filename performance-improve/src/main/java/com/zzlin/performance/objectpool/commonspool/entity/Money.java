package com.zzlin.performance.objectpool.commonspool.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zlin
 * @date 20220320
 */
@Data
public class Money {

    private String type;
    private BigDecimal amount;

    public Money(String type, BigDecimal amount) {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.type = type;
        this.amount = amount;
    }

    public static Money init() {
        // 假设对象new非常耗时
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Money("USD", new BigDecimal("1"));
    }
}
