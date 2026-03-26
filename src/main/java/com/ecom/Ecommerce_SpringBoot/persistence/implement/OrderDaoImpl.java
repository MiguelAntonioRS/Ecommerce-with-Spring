package com.ecom.Ecommerce_SpringBoot.persistence.implement;

import com.ecom.Ecommerce_SpringBoot.contant.StatusOrder;
import com.ecom.Ecommerce_SpringBoot.entities.AddressOrder;
import com.ecom.Ecommerce_SpringBoot.entities.Cart;
import com.ecom.Ecommerce_SpringBoot.entities.ProductOrder;
import com.ecom.Ecommerce_SpringBoot.entities.RequestOrder;
import com.ecom.Ecommerce_SpringBoot.persistence.OrderDAO;
import com.ecom.Ecommerce_SpringBoot.repository.CartRepository;
import com.ecom.Ecommerce_SpringBoot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDAO {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

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

            productOrder.setStatus(StatusOrder.IN_PROGRESS.name());
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
        return null;
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
