package com.sid.gl.config;

import com.sid.gl.event.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Configuration
public class PublisherConfig {
    @Bean
    public Sinks.Many<OrderEvent> orderSink(){
        return Sinks.many().multicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<OrderEvent>> orderSupplier(Sinks.Many<OrderEvent> orderEventSinks){
        return orderEventSinks::asFlux;
    }
}
