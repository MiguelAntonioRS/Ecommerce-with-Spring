package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    void saveOrder(int userId, RequestOrder requestOrder) throws Exception;

    List<ProductOrder> getOrdersByUser(int userId);

    ProductOrder orderStatusUpdate(int id, String status);

    List<ProductOrder> getAllOrdersByUser();
}
