package com.sid.gl.dto;

import com.sid.gl.event.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {
    private Integer userId;
    private Integer amount;
    private Integer productId;
    private Integer orderId;
    private OrderStatus orderStatus;
}
