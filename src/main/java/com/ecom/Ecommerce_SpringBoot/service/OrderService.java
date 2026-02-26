package com.ecom.Ecommerce_SpringBoot.service;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    public void saveOrder(int userId, RequestOrder requestOrder);

    public List<ProductOrder> getOrdersByUser(int userId);

    public ProductOrder orderStatusUpdate(int id, String status);

    public List<ProductOrder> getAllOrdersByUser();
}
