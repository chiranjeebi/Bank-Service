package com.bank.idbi.Service;

import com.bank.idbi.dto.AccountDTO;
import com.bank.idbi.entity.AccountEntity;
import com.bank.idbi.entity.CustomerEntity;
import com.bank.idbi.repository.AccountRepository;
import com.bank.idbi.repository.CustomerRepository;
import com.bank.idbi.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {
    @InjectMocks
    private CustomerRepository customerRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerEntity customer;

/*    @Test
void testCredit(){
        AccountDTO accountDTO=new AccountDTO();
        accountDTO.setAccountId(1l);
        accountDTO.setBalance(900.00);

        CustomerEntity customerEntity=new CustomerEntity();
        customerEntity.setId(1l);
        customerEntity.setOwnerEmail("jane@gmail.com");
        List<CustomerEntity> customerEntities=new ArrayList<>();
        customerEntities.add(customerEntity);
ResponseEntity<CustomerEntity> responseEntity=a
        Mockito.when(customerRepository.findById(1l)).thenReturn(accountDTO.setAccountId());



}*/

}
