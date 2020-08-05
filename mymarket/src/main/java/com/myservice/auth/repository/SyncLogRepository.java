package com.myservice.auth.repository;

import com.myservice.auth.model.SyncLog;
import com.myservice.auth.model.MarketParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SyncLogRepository extends JpaRepository<SyncLog, Long>, JpaSpecificationExecutor<SyncLog> {
}