package com.myservice.mymarket.repository;

import com.myservice.mymarket.model.Product;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.common.repository.IRepository;

public interface ProductRepository extends IRepository<Long, Product, ProductDTO> {

    Product findByCode(String code);
}