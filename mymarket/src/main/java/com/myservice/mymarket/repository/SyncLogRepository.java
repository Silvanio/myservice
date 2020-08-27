package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.SyncLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SyncLogRepository extends JpaRepository<SyncLog, Long>, JpaSpecificationExecutor<SyncLog> {
}