package com.revature.storefront.service;

import com.revature.storefront.model.Order;
import com.revature.storefront.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order){
        return orderRepository.save(order);
    }

    public Order getOrderById(Integer orderId){
        return orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getAllCustomerOrders(Integer customerId){
        return orderRepository.findAllByCustomer_CustomerId(customerId);
    }
}
