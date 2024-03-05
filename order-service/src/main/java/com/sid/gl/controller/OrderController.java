package com.sid.gl.controller;

import com.sid.gl.dto.OrderRequestDTO;
import com.sid.gl.entity.PurchaseOrder;
import com.sid.gl.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
   private final  OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public PurchaseOrder createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
      return orderService.createOrder(orderRequestDTO);
    }
    @GetMapping("/order")
    public List<PurchaseOrder> listPurchase(){
        return orderService.findAll();
    }
}
