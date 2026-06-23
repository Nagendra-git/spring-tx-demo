package com.example.txdemo.repository;

import com.example.txdemo.entity.TransactionLog2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLog2Repository extends JpaRepository<TransactionLog2, Long> {}
