package com.revature.storefront.service;

import com.revature.storefront.model.Customer;
import com.revature.storefront.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

//    @Autowired // this is redundant and implicit
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Integer customerId){
                                                                    // method reference
        return customerRepository.findById(customerId).orElseThrow(RuntimeException::new);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
}
