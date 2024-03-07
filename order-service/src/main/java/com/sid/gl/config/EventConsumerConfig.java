package com.sid.gl.config;

import com.sid.gl.event.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class EventConsumerConfig {
    @Autowired
    private OrderStatusHandler orderStatusHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        log.info("enter here for set status ...");
      return (payment)-> orderStatusHandler.updateOrder(payment.getPaymentRequestDTO().getOrderId(), po->{
          log.info("enter payment status for updating {} ",payment.getPaymentStatus());
          po.setPaymentStatus(payment.getPaymentStatus());
      });
    }
}
