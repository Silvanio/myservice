package com.myservice.auth.service.impl;

import com.myservice.auth.model.Product;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.ProductDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.repository.ProductRepository;
import com.myservice.auth.repository.StockRepository;
import com.myservice.auth.service.ProductService;
import com.myservice.auth.service.StockService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ProductServiceImpl extends MyService<Long, Product, ProductDTO> implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductRepository getRepository() {
        return productRepository;
    }
}
