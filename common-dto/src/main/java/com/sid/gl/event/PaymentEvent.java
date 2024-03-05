package com.sid.gl.event;

import com.sid.gl.dto.PaymentRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class PaymentEvent implements Event {
    private UUID eventId=UUID.randomUUID();
    private Date date=new Date();
    private PaymentRequestDTO paymentRequestDTO;
    private PaymentStatus paymentStatus;

    @Override
    public UUID getEventId() {
        return this.eventId;
    }

    @Override
    public Date getDate() {
        return this.date;
    }

    public PaymentEvent(PaymentRequestDTO paymentRequestDTO, PaymentStatus paymentStatus) {
        this.paymentRequestDTO = paymentRequestDTO;
        this.paymentStatus = paymentStatus;
    }
}
