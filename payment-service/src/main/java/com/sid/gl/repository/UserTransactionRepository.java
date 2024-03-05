package com.sid.gl.repository;

import com.sid.gl.entity.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
}
