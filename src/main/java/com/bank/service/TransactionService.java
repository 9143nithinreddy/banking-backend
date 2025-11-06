package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.model.Transaction;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction transfer(String senderAcc, String receiverAcc, double amount) {
        Account sender = accountRepository.findByAccountNumber(senderAcc);
        Account receiver = accountRepository.findByAccountNumber(receiverAcc);

        if (sender == null)
            throw new RuntimeException("Sender account not found: " + senderAcc);
        if (receiver == null)
            throw new RuntimeException("Receiver account not found: " + receiverAcc);
        if (sender.getBalance() < amount)
            throw new RuntimeException("Insufficient balance in sender account: " + senderAcc);


        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        Transaction txn = new Transaction(senderAcc, receiverAcc, amount);
        return transactionRepository.save(txn);
    }

    public List<Transaction> getHistory(String accountNumber) {
        return transactionRepository.findBySenderAccountOrReceiverAccount(accountNumber, accountNumber);
    }
}
