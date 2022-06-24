package com.bank.idbi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BeneficiaryDTO {
    private Long customerId;
    private List<CustomerDTO> beneficiaries;
}
