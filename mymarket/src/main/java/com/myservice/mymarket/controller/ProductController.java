package com.myservice.mymarket.controller;

import com.myservice.common.domain.StatusEnum;
import com.myservice.mymarket.model.Product;
import com.myservice.mymarket.model.dto.ProductDTO;
import com.myservice.mymarket.service.ProductService;
import com.myservice.common.controller.MyController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(value = "Product API")
public class ProductController extends MyController<Long, Product, ProductDTO> {

    @Autowired
    ProductService productService;

    @ApiOperation(value = "List for HighLightsStock", response = List.class)
    @GetMapping("/listForHighLightsStock")
    public Collection<ProductDTO> listForHighLightsStock() {
        Collection<Product> content = this.getService().listForHighLightsStock();
        return convertListEntityToDTO(content) ;
    }

    @Override
    public void permitAuthorities(List<String> authorities) {
        authorities.add("MY_MARKET_ADMIN");
    }

    @Override
    public ProductService getService() {
        return productService;
    }

}