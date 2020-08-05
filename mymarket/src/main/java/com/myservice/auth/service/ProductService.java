package com.myservice.auth.service;

import com.myservice.auth.model.Product;
import com.myservice.auth.model.dto.ProductDTO;
import com.myservice.common.service.IService;

public interface ProductService extends IService<Long, Product, ProductDTO> {

}
