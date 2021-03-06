package com.myservice.mymarket.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportFileService{

    void uploadStocks(MultipartFile file);

    void uploadSectors(MultipartFile file);

    void uploadSegmentFII(MultipartFile file);

    void uploadHistoricalIBOV(MultipartFile file);
}
