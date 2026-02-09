package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import org.springframework.stereotype.Component;

@Component
public interface OrderDAO {

    public void saveOrder(int userId, RequestOrder requestOrder);
}
