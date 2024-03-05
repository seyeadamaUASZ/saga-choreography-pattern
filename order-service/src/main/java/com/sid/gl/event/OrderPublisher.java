package com.sid.gl.event;

import com.sid.gl.dto.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> orderSinks;

    public void publishOrderEvent(OrderRequestDTO orderRequestDTO,
                                  OrderStatus orderStatus){
        OrderEvent orderEvent=
                new OrderEvent(orderRequestDTO,orderStatus);
        orderSinks.tryEmitNext(orderEvent);

    }
}
