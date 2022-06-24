package com.bank.idbi.service.impl;

import com.bank.idbi.dto.*;
import com.bank.idbi.entity.AccountEntity;
import com.bank.idbi.entity.CustomerEntity;
import com.bank.idbi.entity.TransactionEntity;
import com.bank.idbi.exception.BusinessException;
import com.bank.idbi.exception.ErrorModel;
import com.bank.idbi.repository.AccountRepository;
import com.bank.idbi.repository.CustomerRepository;
import com.bank.idbi.repository.TransactionRepository;
import com.bank.idbi.service.AccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class AccountServiceimpl implements AccountService {
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public AccountDTO credit(Long customerId, Double amount) {
        AccountDTO accountDTO = new AccountDTO();
        Optional<CustomerEntity> custEntity = customerRepository.findById(customerId);
        if (custEntity.isPresent()) {
            Long accNumber = custEntity.get().getAccount().getAccountId();
            Optional<AccountEntity> accEntity = accountRepository.findById(accNumber);
            AccountEntity accountEntity = null;

            if (accEntity.isPresent()) {
                accountEntity = accEntity.get();
                Double balance = accountEntity.getBalance();
                Double newBalance = balance + amount;
                accountEntity.setBalance(newBalance);
                accountEntity = accountRepository.save(accountEntity);
                BeanUtils.copyProperties(accountEntity, accountDTO);

                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setAmount(amount);
                transactionEntity.setCustomerId(customerId);
                transactionEntity.setTransactionType("Credit");
                transactionEntity.setTime(LocalDateTime.now());
                transactionEntity = transactionRepository.save(transactionEntity);
            }
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no account found");
            model.setCode("ACCOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);

        }


        return accountDTO;
    }

    @Override
    public AccountDTO debit(Long customerId, Double amount) {
        AccountDTO accountDTO = new AccountDTO();
        Optional<CustomerEntity> custEntity = customerRepository.findById(customerId);
        if (custEntity.isPresent()) {
            Long accNumber = custEntity.get().getAccount().getAccountId();
            Optional<AccountEntity> accEntity = accountRepository.findById(accNumber);
            AccountEntity accountEntity = null;

            if (accEntity.isPresent()) {
                accountEntity = accEntity.get();
                Double balance = accountEntity.getBalance();
                Double newBalance = balance - amount;
                accountEntity.setBalance(newBalance);
                accountEntity = accountRepository.save(accountEntity);
                BeanUtils.copyProperties(accountEntity, accountDTO);

                TransactionEntity transactionEntity = new TransactionEntity();
                transactionEntity.setAmount(amount);
                transactionEntity.setCustomerId(customerId);
                transactionEntity.setTransactionType("Debit");
                transactionEntity.setTime(LocalDateTime.now());
                transactionEntity = transactionRepository.save(transactionEntity);
            }
        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no account found");
            model.setCode("ACCOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);

        }


        return accountDTO;
    }

    @Override
    public List<TransactionDTO> getAllTransactions(Long customerId) {
        Optional<List<TransactionEntity>> transactionEntityList = transactionRepository.findByCustomerId(customerId);
        List<TransactionDTO> transactionList = new ArrayList<>();
        if (transactionEntityList.isPresent()) {
            List<TransactionEntity> transactionsList = transactionEntityList.get();

            for (TransactionEntity entity : transactionsList) {
                TransactionDTO transDTO = new TransactionDTO();
                transDTO.setTransactionId(entity.getTransactionId());
                transDTO.setTransactionType(entity.getTransactionType());
                transDTO.setAmount(entity.getAmount());
                transDTO.setTime(entity.getTime());
                transDTO.setCustomerId(entity.getCustomerId());
                transactionList.add(transDTO);
            }

        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no transactions found");
            model.setCode("TRANSACT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);


        }
        return transactionList;
    }

    @Override
    public List<CustomerDTO> addBeneficiary(BeneficiaryDTO beneficiaryDTO) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(beneficiaryDTO.getCustomerId());
        List<CustomerDTO> beneficairyList = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        if (custEntity.isPresent()) {
            for (CustomerDTO c : beneficiaryDTO.getBeneficiaries()) {
                Optional<CustomerEntity> benefiti = customerRepository.findById(c.getId());
                if (benefiti.isPresent()) {
                    CustomerDTO bene = new CustomerDTO();
                    bene.setOwnerEmail(benefiti.get().getOwnerEmail());
                    bene.setOwnerName(benefiti.get().getOwnerName());
                    beneficairyList.add(bene);
                    sb.append(c.getId().toString());
                    sb.append(",");

                } else {
                    ErrorModel model = new ErrorModel();
                    model.setMessage("Sorry no beneficiary account found for " + c.getId());
                    model.setCode("ACCOUNT_002");
                    List<ErrorModel> errors = new ArrayList<>();
                    errors.add(model);
                    throw new BusinessException(errors);
                }

            }
            sb.deleteCharAt(sb.toString().length() - 1);
            CustomerEntity customerEntity = custEntity.get();
            customerEntity.setBeneficiaries(sb.toString());
            customerEntity = customerRepository.save(customerEntity);


        } else {
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no account found");
            model.setCode("ACCOUNT_001");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);

        }

        return beneficairyList;
    }

    @Override
    public String transferMoney(TransferDTO transferDTO) {
        Optional<CustomerEntity> custEntity = customerRepository.findById(transferDTO.getCustId());
        if (custEntity.isPresent()) {
            CustomerEntity cust = custEntity.get();
            cust.getAccount().getBalance();
            if (transferDTO.getAmount() > cust.getAccount().getBalance()) {
                ErrorModel model = new ErrorModel();
                model.setMessage("Sorry you do not have sufficient balance in your account to make a transfer");
                model.setCode("TRANSFER_001");
                List<ErrorModel> errors = new ArrayList<>();
                errors.add(model);
                throw new BusinessException(errors);

            }
            else {
                Optional<CustomerEntity> bene = customerRepository.findById(transferDTO.getBeneficiaryId());
                if (bene.isPresent()) {
                    CustomerEntity custEn = bene.get();
                    Double newAmountBene = custEn.getAccount().getBalance() + transferDTO.getAmount();
                    custEn.getAccount().setBalance(newAmountBene);
                    custEn = customerRepository.save(custEn);
                    Double newAmountCust = cust.getAccount().getBalance() - transferDTO.getAmount();
                    cust.getAccount().setBalance(newAmountCust);
                    cust=customerRepository.save(cust);

                } else {
                    ErrorModel model = new ErrorModel();
                    model.setMessage("Sorry no beneficiary account found for " + transferDTO.getBeneficiaryId());
                    model.setCode("ACCOUNT_002");
                    List<ErrorModel> errors = new ArrayList<>();
                    errors.add(model);
                    throw new BusinessException(errors);
                }
            }


        }
        else{
            ErrorModel model = new ErrorModel();
            model.setMessage("Sorry no customer account found for " + transferDTO.getCustId());
            model.setCode("ACCOUNT_002");
            List<ErrorModel> errors = new ArrayList<>();
            errors.add(model);
            throw new BusinessException(errors);
        }
        return "Sucessfully transferred your money";

    }
}
