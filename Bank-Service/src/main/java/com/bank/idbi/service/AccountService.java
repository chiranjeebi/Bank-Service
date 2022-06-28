package com.bank.idbi.service;

import com.bank.idbi.dto.*;

import java.util.List;

public interface  AccountService {
    public   AccountDTO credit(Long customerId, Double amount);
 public    AccountDTO debit(Long customerId, Double balance);
    public  List<TransactionDTO> getAllTransactions(Long customerId);
    public  List<CustomerDTO> addBeneficiary(BeneficiaryDTO beneficiaryDTO);
    public   String transferMoney(TransferDTO transferDTO);
/*    public AccountDTO getAccountDetails(Long accountNumber);//*/
void deleteCustomer(Long customerId);

}
