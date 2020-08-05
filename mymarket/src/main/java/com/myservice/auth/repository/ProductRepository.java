package com.myservice.auth.repository;

import com.myservice.auth.model.Product;
import com.myservice.auth.model.dto.ProductDTO;
import com.myservice.common.repository.IRepository;

public interface ProductRepository extends IRepository<Long, Product, ProductDTO> {

    Product findByCode(String code);
}