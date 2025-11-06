package com.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.model.AuditLog;
import com.bank.repository.AuditLogRepository;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditRepo;

    public void record(String endpoint, String method, String username, String details) {
        AuditLog log = new AuditLog(endpoint, method, username, details);
        auditRepo.save(log);
        System.out.println("🧾 AUDIT: [" + method + "] " + endpoint + " | User: " + username + " | " + details);
    }
}
