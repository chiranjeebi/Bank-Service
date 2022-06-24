package com.bank.idbi.repository;

import com.bank.idbi.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AccountRepository extends JpaRepository<AccountEntity,Long> {

}
