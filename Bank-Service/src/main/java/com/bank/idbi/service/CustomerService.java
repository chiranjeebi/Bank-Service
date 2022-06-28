package com.bank.idbi.service;

import com.bank.idbi.dto.CustomerDTO;

public interface CustomerService {
    CustomerDTO register(CustomerDTO customerDTO);
    String login(String email, String password);


}
