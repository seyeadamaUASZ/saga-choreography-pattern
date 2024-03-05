package com.sid.gl.service;

import com.sid.gl.dto.OrderRequestDTO;
import com.sid.gl.entity.PurchaseOrder;
import com.sid.gl.event.OrderPublisher;
import com.sid.gl.event.OrderStatus;
import com.sid.gl.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderPublisher orderPublisher;

    public OrderService(OrderRepository orderRepository, OrderPublisher orderPublisher) {
        this.orderRepository = orderRepository;
        this.orderPublisher = orderPublisher;
    }

    @Transactional
    public PurchaseOrder createOrder(OrderRequestDTO orderRequestDTO){
      PurchaseOrder order= orderRepository.save(convertToPurchase(orderRequestDTO));
      orderRequestDTO.setOrderId(order.getId());
      //produce kafka event order event in Order Created status
      orderPublisher.publishOrderEvent(orderRequestDTO,OrderStatus.ORDER_CREATED);
      return order;
    }

    public List<PurchaseOrder> findAll(){
        return orderRepository.findAll();
    }

    private PurchaseOrder convertToPurchase(OrderRequestDTO orderRequestDTO){
        PurchaseOrder purchaseOrder=
                new PurchaseOrder();
        purchaseOrder.setUserId(orderRequestDTO.getUserId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setProductId(orderRequestDTO.getProductId());
        purchaseOrder.setPrice(orderRequestDTO.getAmount());
        return purchaseOrder;
    }
}
