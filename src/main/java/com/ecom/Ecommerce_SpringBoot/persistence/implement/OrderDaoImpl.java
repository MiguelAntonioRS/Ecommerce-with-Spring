package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import com.ecom.Ecommerce_SpringBoot.persistence.OrderDAO;
import org.springframework.stereotype.Component;

@Component
public class OrderDaoImpl implements OrderDAO {

    @Override
    public ProductOrder saveOrder(int userId, RequestOrder requestOrder) {
        return null;
    }
}
