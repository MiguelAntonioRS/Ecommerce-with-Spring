package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    public void saveOrder(int userId, RequestOrder requestOrder);
}
