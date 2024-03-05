package com.sid.gl.service;


import com.sid.gl.dto.OrderRequestDTO;
import com.sid.gl.dto.PaymentRequestDTO;
import com.sid.gl.entity.UserBalance;
import com.sid.gl.entity.UserTransaction;
import com.sid.gl.event.OrderEvent;
import com.sid.gl.event.PaymentEvent;
import com.sid.gl.event.PaymentStatus;
import com.sid.gl.repository.UserBalanceRepository;
import com.sid.gl.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PaymentService {
    private final UserBalanceRepository userBalanceRepository;
    private final UserTransactionRepository userTransactionRepository;

    public PaymentService(UserBalanceRepository userBalanceRepository, UserTransactionRepository userTransactionRepository) {
        this.userBalanceRepository = userBalanceRepository;
        this.userTransactionRepository = userTransactionRepository;
    }

    @PostConstruct
    public void initBalanceUser(){
        userBalanceRepository.saveAll(
                Stream.of(new UserBalance(
                        101,5000
                ),
                        new UserBalance(102,4700),
                        new UserBalance(103,5700),
                        new UserBalance(104,6000),
                        new UserBalance(105,5500)).collect(Collectors.toList())
        );
    }
    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDTO orderRequestDTO=
                orderEvent.getOrderRequestDTO();

        PaymentRequestDTO paymentRequestDTO=
                new PaymentRequestDTO(orderRequestDTO.getUserId(), orderRequestDTO.getOrderId(), orderRequestDTO.getAmount());
        return userBalanceRepository.findById(orderRequestDTO.getUserId())
                .filter(userBalance -> userBalance.getPrice() > orderRequestDTO.getAmount())
                .map(userBalance -> {
                    userBalance.setPrice(userBalance.getPrice()- orderRequestDTO.getAmount());
                    //userBalanceRepository.save(userBalance);
                    userTransactionRepository.save(new UserTransaction(orderRequestDTO.getOrderId(), orderRequestDTO.getUserId(), orderRequestDTO.getAmount()));
                return new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_COMPLETED);
                }).orElse(new PaymentEvent(paymentRequestDTO,PaymentStatus.PAYMENT_FAILED));
    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userTransactionRepository.findById(orderEvent.getOrderRequestDTO().getOrderId())
                .ifPresent(ut->{
                    userTransactionRepository.delete(ut);
                    userTransactionRepository.findById(ut.getUserId())
                            .ifPresent(ub->ub.setAmount(ub.getAmount()+ut.getAmount()));
                });
    }
}
