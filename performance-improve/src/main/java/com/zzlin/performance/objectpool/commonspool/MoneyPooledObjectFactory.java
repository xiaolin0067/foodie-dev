package com.zzlin.performance.objectpool.commonspool;

import com.zzlin.performance.objectpool.commonspool.entity.Money;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.math.BigDecimal;

/**
 * @author zlin
 * @date 20220320
 */
@Slf4j
public class MoneyPooledObjectFactory implements PooledObjectFactory<Money> {

    @Override
    public PooledObject<Money> makeObject() throws Exception {
        DefaultPooledObject<Money> pooledObject =
                new DefaultPooledObject<>(new Money("USD", new BigDecimal(1)));
        log.info("MoneyPooledObjectFactory..makeObject..state={}", pooledObject.getState());
        return pooledObject;
    }

    @Override
    public void destroyObject(PooledObject<Money> pooledObject) throws Exception {
        log.info("MoneyPooledObjectFactory..destroyObject..state={}", pooledObject.getState());
    }

    @Override
    public boolean validateObject(PooledObject<Money> pooledObject) {
        log.info("MoneyPooledObjectFactory..validateObject..state={}", pooledObject.getState());
        return true;
    }

    @Override
    public void activateObject(PooledObject<Money> pooledObject) throws Exception {
        log.info("MoneyPooledObjectFactory..activateObject..state={}", pooledObject.getState());
    }

    @Override
    public void passivateObject(PooledObject<Money> pooledObject) throws Exception {
        log.info("MoneyPooledObjectFactory..passivateObject..state={}", pooledObject.getState());
    }
}
