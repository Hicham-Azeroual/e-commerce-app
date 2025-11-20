package com.hicham.customer.service;

import com.hicham.customer.Entity.Customer;
import com.hicham.customer.dto.CustomerRequest;
import com.hicham.customer.dto.CustomerResponse;
import com.hicham.customer.exception.CustomerNotFoundException;
import com.hicham.customer.mapper.CustomerMapper;
import com.hicham.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerMapper.toCustomer(customerRequest);
        customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }

    public CustomerResponse updateCustomer(String id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        if (customer == null) {
            return null;
        }
        mergeCustomer(customer, request);
        customerRepository.save(customer);
        return customerMapper.fromCustomer(customer);
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::fromCustomer).toList();
    }

    public CustomerResponse findCustomerById(String id) {
        return customerRepository.findById(id).map(customerMapper::fromCustomer).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    public boolean exitById(String id) {
        return customerRepository.existsById(id);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }


    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

}
