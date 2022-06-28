package com.bank.idbi.service.impl;

import com.bank.idbi.dto.AccountDTO;
import com.bank.idbi.dto.CustomerDTO;
import com.bank.idbi.entity.AccountEntity;
import com.bank.idbi.entity.CustomerEntity;
import com.bank.idbi.exception.BusinessException;
import com.bank.idbi.exception.ErrorModel;
import com.bank.idbi.repository.AccountRepository;
import com.bank.idbi.repository.CustomerRepository;
import com.bank.idbi.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public CustomerDTO register(CustomerDTO customerDTO) {
        Optional<CustomerEntity> customer = customerRepository.findByPhone(customerDTO.getPhone());
        List<ErrorModel> errors = null;
        if (customer.isPresent()) {
            ErrorModel error = new ErrorModel();
            error.setCode("EXIST");
            error.setMessage(" Sucessfully registered");
            errors = new ArrayList<>();
            errors.add(error);
        } else {
            CustomerEntity customerEntity = new CustomerEntity();
            AccountEntity accentity = new AccountEntity();
            accentity.setBalance(0.0);
           // accentity.setAccountId(1L);//
            customerEntity.setAccount(accentity);
            BeanUtils.copyProperties(customerDTO, customerEntity);
            customerEntity = customerRepository.save(customerEntity);

            BeanUtils.copyProperties(customerEntity, customerDTO);
            customerDTO.setAccountNo(customerEntity.getAccount().getAccountId());
            customerDTO.setOwnerName(customerEntity.getOwnerName());//
          customerDTO.setOwnerEmail(customerEntity.getOwnerEmail());
        }
        if (errors != null) {
            throw new BusinessException(errors);
        } else {
            return customerDTO;
        }
    }


    @Override
    public String login(String email, String password) {
        String response = "";
        Optional<CustomerEntity> custEntity = customerRepository.findByOwnerEmailAndPassword(email, password);
        Optional<AccountEntity> accountEntityOptional=accountRepository.findById(1L);
        if (custEntity.isPresent()) {
            response = "login successfully";


            //in this we have to return customer details
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("login failed");
            model.setCode("AUTH_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        return response;
    }

 /*   @Override
    public String login(String email, String password) {
        String response = "";
        Optional<CustomerEntity> custEntity = customerRepository.findByOwnerEmailAndPassword(email, password);
        Optional<AccountEntity> accountEntityOptional=accountRepository.findById(1L);
        if (custEntity.isPresent()) {
            response = "login successfully";


   //in this we have to return customer details
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("login failed");
            model.setCode("AUTH_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        return response;
    }
*/





}

