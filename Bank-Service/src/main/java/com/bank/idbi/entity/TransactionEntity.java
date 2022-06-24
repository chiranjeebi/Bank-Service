package com.bank.idbi.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANSACTION_TABLE")
@Getter
@Setter
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long transactionId;
    private Long customerId;
    private String transactionType;
    private Double amount;
    private LocalDateTime time;
}
