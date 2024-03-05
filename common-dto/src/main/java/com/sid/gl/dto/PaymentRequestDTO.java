package com.sid.gl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private Integer userId;
    private Integer amount;
    private Integer orderId;
}
