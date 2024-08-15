package com.microservice.accounts.controller;

import com.microservice.accounts.constant.AccountsConstants;
import com.microservice.accounts.dto.CustomerDto;
import com.microservice.accounts.dto.ResponseDto;
import com.microservice.accounts.service.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AccountsController {
    public final IAccountService iAccountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto){
        /* create customer and account */
        iAccountService.createAccount(customerDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam("mobile_number") String mobileNumber){
        /* get customer details */
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iAccountService.fetchAccount(mobileNumber));
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@RequestBody CustomerDto customerDto){
        /* update customer and account details */
        boolean isUpdated = iAccountService.updateAccount(customerDto);
        if (isUpdated)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }
}
