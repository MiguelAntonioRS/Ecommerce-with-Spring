package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public ProductOrder saveOrder(int userId);
}
