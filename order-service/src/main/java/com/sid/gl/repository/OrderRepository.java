package com.sid.gl.repository;

import com.sid.gl.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder,Integer> {
}
