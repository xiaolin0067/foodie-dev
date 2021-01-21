package com.zzlin.job;

import com.zzlin.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zlin
 * @date 20210120
 */
@Component
public class OrderJob {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderJob.class);

    @Resource
    private OrderService orderService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void autoCloseOrder() {
        LOGGER.info("关闭支付超时的订单");
        orderService.closePayOvertimeOrder();
    }
}
