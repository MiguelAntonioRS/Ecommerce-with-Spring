package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDAO {

    void saveOrder(int userId, RequestOrder requestOrder);

    List<ProductOrder> getOrdersByUser(int userId);

    ProductOrder orderStatusUpdate(int id, String status);

    List<ProductOrder> getAllOrdersByUser();
}
