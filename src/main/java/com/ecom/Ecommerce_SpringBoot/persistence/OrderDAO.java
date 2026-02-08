package com.ecom.Ecommerce_SpringBoot.persistence;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import org.springframework.stereotype.Component;

@Component
public interface OrderDAO {

    public ProductOrder saveOrder(int userId);
}
