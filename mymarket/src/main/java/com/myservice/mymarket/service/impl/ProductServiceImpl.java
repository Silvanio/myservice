package com.myservice.mymarket.service.impl;

import com.myservice.mymarket.model.Product;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.mymarket.repository.ProductRepository;
import com.myservice.mymarket.service.ProductService;
import com.myservice.common.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl extends MyService<Long, Product, ProductDTO> implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Collection<Product> listForHighLightsStock() {
        List<Product> products = new ArrayList<>();
        products.add(productRepository.findByCode("STOCK"));
        products.add(productRepository.findByCode("ETF"));
        products.add(productRepository.findByCode("FII"));
        products.add(productRepository.findByCode("INDEX"));
        products.add(productRepository.findByCode("FUNDS"));
        products.add(productRepository.findByCode("BDR"));
        products.add(productRepository.findByCode("CRIPTO"));
        return products;
    }

    @Override
    public ProductRepository getRepository() {
        return productRepository;
    }
}
