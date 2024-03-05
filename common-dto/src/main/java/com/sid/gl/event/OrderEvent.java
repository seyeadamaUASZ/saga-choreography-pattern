package com.sid.gl.event;

import com.sid.gl.dto.OrderRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;


@NoArgsConstructor
@Data
public class OrderEvent implements Event{
    private UUID eventId = UUID.randomUUID();
    private Date date = new Date();
    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;


    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
        this.orderRequestDTO = orderRequestDTO;
        this.orderStatus = orderStatus;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

}