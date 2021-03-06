package com.myservice.mymarket.controller;

import com.myservice.mymarket.service.SyncService;
import com.myservice.common.dto.common.ResponseMessageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sync")
@Api(value = "Sync API")
@PreAuthorize("hasAnyAuthority(this.listAuthorities())")
public class SyncController implements Serializable {

    @Autowired
    SyncService syncService;

    public List<String> listAuthorities() {
        List<String> authorities = new ArrayList<>();
        authorities.add("MY_MARKET_ADMIN");
        return authorities;
    }

    @ApiOperation(value = "Sync Historical Data Stock", response = ResponseMessageDTO.class)
    @GetMapping("/syncHistoricalDataStock")
    public ResponseMessageDTO syncHistoricalDataStock() {
        this.syncService.syncHistoricalDataStock();
        return ResponseMessageDTO.get("msg_sync_processing");
    }

    @ApiOperation(value = "Sync Quote Stock", response = ResponseMessageDTO.class)
    @GetMapping("/syncQuoteStock")
    public ResponseMessageDTO syncQuoteStock() {
        this.syncService.syncQuoteStock();
        return ResponseMessageDTO.get("msg_sync_processing");
    }

    @ApiOperation(value = "Sync Quote CRYPTO", response = ResponseMessageDTO.class)
    @GetMapping("/syncQuoteCrypto")
    public ResponseMessageDTO syncQuoteCrypto() {
        this.syncService.syncQuoteCrypto();
        return ResponseMessageDTO.get("msg_sync_processing");
    }

    @ApiOperation(value = "Sync Quote Ibovespa", response = ResponseMessageDTO.class)
    @GetMapping("/syncHistoricalIbovespa")
    public ResponseMessageDTO syncHistoricalIbovespa() {
        this.syncService.syncHistoricalIbovespa();
        return ResponseMessageDTO.get("msg_sync_processing");
    }

}