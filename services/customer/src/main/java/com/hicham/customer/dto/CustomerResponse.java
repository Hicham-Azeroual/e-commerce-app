package com.hicham.customer.dto;

import com.hicham.customer.Entity.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address

) {
}
