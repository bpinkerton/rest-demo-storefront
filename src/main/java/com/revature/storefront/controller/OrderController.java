package com.revature.storefront.controller;

import com.revature.storefront.model.Order;
import com.revature.storefront.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order){
        Order dbOrder = orderService.createOrder(order);
        dbOrder.add(linkTo(OrderController.class).slash(dbOrder.getOrderId()).withSelfRel());
        // add a second link to the embedded resource withRel of whatever resource we're linking to
        dbOrder.getCustomer().add(
                linkTo(CustomerController.class)
                        .slash(dbOrder.getCustomer().getCustomerId())
                        .withRel("customer"));

        return dbOrder;
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Integer orderId){
        Order dbOrder = orderService.getOrderById(orderId);
        dbOrder.add(linkTo(OrderController.class).slash(dbOrder.getOrderId()).withSelfRel());
        dbOrder.getCustomer().add(
                linkTo(CustomerController.class)
                        .slash(dbOrder.getCustomer().getCustomerId())
                        .withRel("customer"));
        return dbOrder;
    }

    @GetMapping
    public List<Order> getAllOrders(){
        List<Order> dbOrders = orderService.getAllOrders();
        dbOrders = dbOrders.stream().map((order) ->
                {
                    order.add(linkTo(OrderController.class).slash(order.getOrderId()).withSelfRel());
                    order.getCustomer().add(
                            linkTo(CustomerController.class)
                                    .slash(order.getCustomer().getCustomerId())
                                    .withRel("customer"));
                    return order;
                }
                ).collect(Collectors.toList());

        return dbOrders;
    }

    //          OPERATION -> get all orders by customer id
    //                 /orders/customer/{id}
    @GetMapping("/customer/{customerId}")
    public List<Order> getAllCustomerOrders(@PathVariable Integer customerId){
        List<Order> dbOrders = orderService.getAllCustomerOrders(customerId);
        dbOrders = dbOrders.stream().map((order) ->
                {
                    order.add(linkTo(OrderController.class).slash(order.getOrderId()).withSelfRel());
                    order.getCustomer().add(
                            linkTo(CustomerController.class)
                                    .slash(order.getCustomer().getCustomerId())
                                    .withRel("customer"));
                    return order;
                }
        ).collect(Collectors.toList());
        return dbOrders;
    }
}
