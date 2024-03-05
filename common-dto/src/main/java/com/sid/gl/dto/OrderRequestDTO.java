package com.sid.gl.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Integer userId;
    private Integer amount;
    private Integer productId;
    private Integer orderId;
}
