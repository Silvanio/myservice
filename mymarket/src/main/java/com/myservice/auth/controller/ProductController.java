package com.myservice.auth.controller;

import com.myservice.auth.model.Product;
import com.myservice.auth.model.Stock;
import com.myservice.auth.model.dto.ProductDTO;
import com.myservice.auth.model.dto.StockDTO;
import com.myservice.auth.service.ProductService;
import com.myservice.auth.service.StockService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@Api(value = "Product API")
public class ProductController extends MyController<Long, Product, ProductDTO> {

    @Autowired
    ProductService productService;

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public ProductService getService() {
        return productService;
    }

}