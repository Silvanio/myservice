package com.myservice.auth.controller;


import com.myservice.auth.service.ImportFileService;
import com.myservice.common.dto.common.ResponseMessageDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/import")
@Api(value = "Import Files")
@PreAuthorize("hasAnyAuthority(this.listAuthorities())")
public class ImportController {

    @Autowired
    ImportFileService importFileService;

    public List<String> listAuthorities() {
        List<String> authorities = new ArrayList<>();
        authorities.add("MY_MARKET_ADMIN");
        return authorities;
    }

    @PostMapping(value = "/uploadStocks", consumes = {"multipart/form-data"})
    public ResponseMessageDTO uploadStocks(@RequestParam("file") MultipartFile file) {
        importFileService.uploadStocks(file);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @PostMapping(value = "/uploadSectors", consumes = {"multipart/form-data"})
    public ResponseMessageDTO uploadSectors(@RequestParam("file") MultipartFile file) {
        importFileService.uploadSectors(file);
        return ResponseMessageDTO.get("msg_general_success");
    }

    @PostMapping(value = "/uploadSegmentFII", consumes = {"multipart/form-data"})
    public ResponseMessageDTO uploadSegmentFII(@RequestParam("file") MultipartFile file) {
        importFileService.uploadSegmentFII(file);
        return ResponseMessageDTO.get("msg_general_success");
    }

}
