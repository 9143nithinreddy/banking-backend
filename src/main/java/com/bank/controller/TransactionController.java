package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.model.Transaction;
import com.bank.service.AuditLogService;
import com.bank.service.TransactionService;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuditLogService auditService;

    @PostMapping("/transfer")
    public Transaction transfer(@RequestParam String sender, @RequestParam String receiver, @RequestParam double amount) {
        Transaction txn = transactionService.transfer(sender, receiver, amount);
        auditService.record("/api/transactions/transfer", "POST", "system",
            "Transferred ₹" + amount + " from " + sender + " → " + receiver);
        return txn;
    }

    @GetMapping("/history/{accountNumber}")
    public List<Transaction> getHistory(@PathVariable String accountNumber) {
        return transactionService.getHistory(accountNumber);
    }
}
