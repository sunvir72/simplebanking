package com.brainridge.simplebanking.controller;

import com.brainridge.simplebanking.dto.CreateAccountRequestDTO;
import com.brainridge.simplebanking.dto.CreateAccountResponseDTO;
import com.brainridge.simplebanking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts/")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    AccountController(AccountService accountService){
        this.accountService = accountService;
    }
    @PostMapping("create")
    public ResponseEntity<CreateAccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        CreateAccountResponseDTO response =  accountService.createAccount(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
