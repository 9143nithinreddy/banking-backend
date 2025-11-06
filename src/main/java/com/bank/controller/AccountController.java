package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.model.Account;
import com.bank.service.AccountService;
import com.bank.service.AuditLogService;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AuditLogService auditService;

    @PostMapping("/create/{userId}")
    public Account createAccount(@PathVariable Long userId, @RequestParam double initialDeposit) {
        Account account = accountService.createAccount(userId, initialDeposit);
        auditService.record("/api/account/create", "POST", "user-" + userId, 
            "Created account: " + account.getAccountNumber() + " with ₹" + initialDeposit);
        return account;
    }    

    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }
}
