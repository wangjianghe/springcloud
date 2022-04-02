package com.wjh.wjhcom.common.mq.rocket.callback;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionCheckListener;
import org.apache.rocketmq.common.message.MessageExt;

@Slf4j
public class DefaultTransactionCheckListener implements TransactionCheckListener {
    @Override
    public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
        log.info("default rollback check listener +"+msg);
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }
}
