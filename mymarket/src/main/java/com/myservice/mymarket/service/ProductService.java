package com.myservice.mymarket.service;

import com.myservice.mymarket.model.Product;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.common.service.IService;

import java.util.Collection;

public interface ProductService extends IService<Long, Product, ProductDTO> {

    Collection<Product> listForHighLightsStock();
}
