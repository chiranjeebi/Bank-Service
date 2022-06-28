package com.bank.idbi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDTO {
    private Long id;
    @NotEmpty(message = " Email cannot be empty")
    private String ownerEmail;
    @NotNull(message = "Name is a required field")
    private String ownerName;
    @NotNull(message = "Password is mandatory")
    private String password;
    @NotNull(message = "Phone Number is mandatory")
    @Size( message = "Not a valid phone number")
    private String phone;
    private String adhaarNo;
    private Long accountNo;
    private String beneficiaries;


    }

