package com.revature.storefront.controller;

import com.revature.storefront.model.Customer;
import com.revature.storefront.service.CustomerService;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        Customer dbCustomer = customerService.createCustomer(customer);

        // to achieve HATEOAS, we need to add links to things
        // localhost:8080/api/v2/customers/1
        Link link = linkTo(CustomerController.class).slash(dbCustomer.getCustomerId()).withSelfRel();

        dbCustomer.add(link);

        return dbCustomer;
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable Integer customerId){
        return customerService.getCustomerById(customerId)
                .add(linkTo(CustomerController.class).slash(customerId).withSelfRel());
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers().stream().map(
                (customer) -> customer.add(linkTo(CustomerController.class).slash(customer.getCustomerId()).withSelfRel())
        ).collect(Collectors.toList());
    }
}
