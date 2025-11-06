package com.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bank.model.AuditLog;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
