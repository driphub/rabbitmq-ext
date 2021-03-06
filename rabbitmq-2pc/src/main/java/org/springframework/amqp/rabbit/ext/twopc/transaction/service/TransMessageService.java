package org.springframework.amqp.rabbit.ext.twopc.transaction.service;

import org.springframework.amqp.rabbit.ext.twopc.transaction.message.TransMessageEntity;

import java.util.List;

/**
 * @author wuwei
 * @className TransMessageService
 * @description
 * @date 2020/1/17 15:55
 **/
public interface TransMessageService {

    /**
     * prepare事务消息
     *
     * @param entity
     */
    void prepareMessage(TransMessageEntity entity);

    /**
     * 消息已投递至mq 等待mq返回confirm确认
     *
     * @param transactionId
     */
    void sendMessage(String transactionId);

    /**
     * 事务消息改为commit状态，已发送至交换机且交换机已确认接收
     *
     * @param transactionId
     */
    void commitMessage(String transactionId);

    /**
     * 回滚事务消息，不发送至mq
     *
     * @param transactionId
     */
    void rollbackMessage(String transactionId);

    /**
     * 事务消息改为需要重新发送状态
     *
     * @param transactionId
     */
    void resendMessage(String transactionId);

    /**
     * 消息设置为重试，幂等判断时需要忽略
     * @param transactionId
     */
    void customerRetryMessage(String transactionId);

    /**
     * 验证消息是否需要幂等验证
     * @param correlationId
     */
    Boolean shouldCheckMessage(String correlationId);

    /**
     * 事务消息被消费者成功消费
     *
     * @param transactionId
     */
    void doneMessage(String transactionId);

    /**
     * 查询需重新发送的事务消息
     *
     * @return
     */
    List<TransMessageEntity> findResendMessages();

    /**
     * 查询本地事务已提交但是状态还是为prepare的事务消息
     *
     * @return
     */
    List<TransMessageEntity> findLocalDoneNotCommit();

}
