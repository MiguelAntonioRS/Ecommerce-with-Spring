package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrderDAO {

    public void saveOrder(int userId, RequestOrder requestOrder);

    public List<ProductOrder> getOrdersByUser(int userId);
}
