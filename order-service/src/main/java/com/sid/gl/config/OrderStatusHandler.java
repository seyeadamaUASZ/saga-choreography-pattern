package com.sid.gl.config;

import com.sid.gl.dto.OrderRequestDTO;
import com.sid.gl.dto.OrderResponseDTO;
import com.sid.gl.entity.PurchaseOrder;
import com.sid.gl.event.OrderPublisher;
import com.sid.gl.event.OrderStatus;
import com.sid.gl.event.PaymentStatus;
import com.sid.gl.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class OrderStatusHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderPublisher orderPublisher;

    @Transactional
    public void updateOrder(int id, Consumer<PurchaseOrder> consumer){
        orderRepository.findById(id).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {
        boolean isCompleted = PaymentStatus.PAYMENT_COMPLETED.equals(purchaseOrder.getPaymentStatus());
        OrderStatus orderStatus= null;
        if(isCompleted)
            orderStatus=OrderStatus.ORDER_COMPLETED;
        else{
            orderStatus = OrderStatus.ORDER_CANCELLED;
            orderPublisher.publishOrderEvent(convertToDTO(purchaseOrder),orderStatus);
        }
        purchaseOrder.setOrderStatus(orderStatus);
    }

    private OrderRequestDTO convertToDTO(PurchaseOrder purchaseOrder){
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderId(purchaseOrder.getId());
        orderRequestDTO.setProductId(purchaseOrder.getProductId());
        orderRequestDTO.setAmount(purchaseOrder.getPrice());
        orderRequestDTO.setUserId(purchaseOrder.getUserId());
        return orderRequestDTO;
    }

}
