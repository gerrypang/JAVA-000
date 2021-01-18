package com.gerry.pang.mq.kafka.producer;

import com.gerry.pang.mq.kafka.Order;

public interface Producer {

    void send(Order order);

    void sendAsync(Order order);

    void close();

    // add your interface method here

    // and then implement it

}
