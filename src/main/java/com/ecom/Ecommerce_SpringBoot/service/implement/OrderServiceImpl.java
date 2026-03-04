package com.ecom.Ecommerce_SpringBoot.service.implement;

import com.ecom.Ecommerce_SpringBoot.entities.AddressOrder;
import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import com.ecom.Ecommerce_SpringBoot.repository.CartRepository;
import com.ecom.Ecommerce_SpringBoot.repository.OrderRepository;
import com.ecom.Ecommerce_SpringBoot.service.OrderService;
import com.ecom.Ecommerce_SpringBoot.util.CommonUtil;
import com.ecom.Ecommerce_SpringBoot.util.StatusOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CommonUtil commonUtil;

    @Override
    public void saveOrder(int userId, RequestOrder requestOrder) {

        List<Cart> cartList = cartRepository.findByUserId(userId);

        for (Cart cart:cartList) {

            ProductOrder productOrder = new ProductOrder();

            productOrder.setOrderId(UUID.randomUUID().toString());
            productOrder.setOrderDate(LocalDate.now());
            productOrder.setProduct(cart.getProduct());
            productOrder.setPrice(cart.getProduct().getDiscountPrice());

            productOrder.setQuantity(cart.getQuantity());
            productOrder.setUser(cart.getUser());

            productOrder.setStatus(StatusOrder.IN_PROGRESS.getName());
            productOrder.setPaymentType(requestOrder.getPaymentType());

            AddressOrder addressOrder = new AddressOrder();
            addressOrder.setFirstName(requestOrder.getFirstName());
            addressOrder.setLastName(requestOrder.getLastName());
            addressOrder.setEmail(requestOrder.getEmail());
            addressOrder.setMobile(requestOrder.getMobile());
            addressOrder.setAddress(requestOrder.getAddress());
            addressOrder.setCity(requestOrder.getCity());
            addressOrder.setState(requestOrder.getState());
            addressOrder.setPincode(requestOrder.getPincode());

            productOrder.setAddressOrder(addressOrder);
            orderRepository.save(productOrder);
        }
    }

    @Override
    public List<ProductOrder> getOrdersByUser(int userId) {

        List<ProductOrder> orders = orderRepository.findByUserId(userId);
        return orders;
    }

    @Override
    public ProductOrder orderStatusUpdate(int id, String status) {

        Optional<ProductOrder> findById = orderRepository.findById(id);

        if (findById.isPresent()) {

            ProductOrder productOrder = findById.get();
            productOrder.setStatus(status);
            ProductOrder updateOrder = orderRepository.save(productOrder);
            return updateOrder;
        }

        return null;
    }

    @Override
    public List<ProductOrder> getAllOrdersByUser() {

        return orderRepository.findAll();
    }
}
