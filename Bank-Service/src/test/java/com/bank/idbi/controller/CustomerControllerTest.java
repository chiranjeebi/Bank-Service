package com.bank.idbi.controller;

import com.bank.idbi.dto.*;
import com.bank.idbi.service.AccountService;
import com.bank.idbi.service.CustomerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;
    @Mock
    private AccountService accountService;
    @Mock
    private CustomerDTO customerDTO;



    @Mock
    private AccountDTO accountDTO;



    @Test
    @DisplayName("success scenario for Credit")
    void testCredit(){
        AccountDTO account=new AccountDTO();
        account.setAccountId(1l);
        account.setBalance(99.99);
Mockito.when(accountService.credit(Mockito.any(),Mockito.anyDouble())).thenReturn(account);
ResponseEntity<AccountDTO> responseEntity=customerController.credit(1l,account);
Assertions.assertEquals(99.99,account.getBalance());
        assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

    }

    @Test
    @DisplayName("success scenario for debit")
    void testDebit(){
        AccountDTO accountDTO1 =new AccountDTO();
        accountDTO1.setBalance(100.00);
        accountDTO1.setAccountId(1l);

        Mockito.when(accountService.debit(Mockito.any(),Mockito.anyDouble())).thenReturn(accountDTO1);
        ResponseEntity<AccountDTO> responseEntity=customerController.debit(1l,accountDTO1);
        Assertions.assertEquals(100.00,responseEntity.getBody().getBalance());//accountDTO1.getBalance()
       Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

    }
    @Test
    @DisplayName("success scenario for Transactions")
void tesGetAllTransactions(){
        TransactionDTO transaction =new TransactionDTO();
        transaction.setTransactionId(1l);
        transaction.setCustomerId(2l);
        transaction.setAmount(500.00);
        List<TransactionDTO>  transactionDTOS =new ArrayList<>();
        transactionDTOS.add(transaction);
        Mockito.when(accountService.getAllTransactions(2l)).thenReturn(transactionDTOS);
        ResponseEntity<List<TransactionDTO>> responseEntity=customerController.getAllTransactions(2l);//form this point it will go to controller
        Assertions.assertEquals(accountService.getAllTransactions(2l),responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

}

/*@Test
@DisplayName("success scenario for add beneficiary")
void testAddBeneficiary(){
BeneficiaryDTO beneficiaryDTO =new BeneficiaryDTO();
beneficiaryDTO.setCustomerId(1l);

List<BeneficiaryDTO> beneficiaryDTOList=new ArrayList<>();
beneficiaryDTOList.add(beneficiaryDTO);
ResponseEntity<List<CustomerDTO>> responseEntity=customerController.addBeneficiary(1l);
Assertions.assertEquals(accountService.addBeneficiary(1l),responseEntity.getBody());
Assertions.assertEquals(HttpStatus.OK.value(),responseEntity.getStatusCodeValue());

}*/
/*    @Test
    void testTransferMoney(){
        TransferDTO transferDTO=new TransferDTO();
        transferDTO.setAmount(100.00);
        transferDTO.setBeneficiaryId(1l);
        List<TransferDTO> list=new ArrayList<>();
        list.add(transferDTO);
        ResponseEntity<String> responseEntity=customerController.transferMoney(transferDTO);
        Mockito.when(accountService.transferMoney(Mockito.any())).thenReturn(responseEntity.getBody());
        Assertions.assertEquals(100.00,transferDTO.getAmount());

    }*/


/*    @Test
void testRegister(){
    CustomerDTO customerDTO1=new CustomerDTO();
    customerDTO1.setId(1l);
    customerDTO1.setOwnerEmail("jhon@gmail.com");
    customerDTO1.setOwnerName("jhon");
    customerDTO1.setPhone("9887888123");

    ResponseEntity<CustomerDTO> customer= customerController.register(customerDTO1);
    Mockito.when(customerService.register(Mockito.any())).thenReturn(customerDTO1);
    Assertions.assertEquals(customerDTO1.getOwnerEmail(),customer.getBody().getOwnerEmail());
    }*/



}
