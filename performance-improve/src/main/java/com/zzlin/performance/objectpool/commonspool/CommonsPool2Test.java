package com.zzlin.performance.objectpool.commonspool;

import com.zzlin.performance.objectpool.commonspool.entity.Money;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author zlin
 * @date 20220320
 */
public class CommonsPool2Test {

    public static void main(String[] args) throws Exception {
        GenericObjectPool<Money> moneyObjectPool = new GenericObjectPool<>(new MoneyPooledObjectFactory());
        Money money = moneyObjectPool.borrowObject();
        money.setType("RMB");
        moneyObjectPool.returnObject(money);
    }

}
