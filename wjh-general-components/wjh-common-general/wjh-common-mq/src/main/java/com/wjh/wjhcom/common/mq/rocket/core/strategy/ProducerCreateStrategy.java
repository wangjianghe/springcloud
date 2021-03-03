package com.wjh.wjhcom.common.mq.rocket.core.strategy;

import java.util.Map;

public interface ProducerCreateStrategy {
    void createProducerContainer(Map<String,Object> map);
}
