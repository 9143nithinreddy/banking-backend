package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.bank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySenderAccountOrReceiverAccount(String sender, String receiver);
}
