package com.mod5r.assesment.controllers;

import com.mod5r.assesment.dto.input.AccountDto;
import com.mod5r.assesment.dto.output.ApiResponse;
import com.mod5r.assesment.entities.Account;
import com.mod5r.assesment.services.AccountService;
import com.mod5r.assesment.services.LocalizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController implements BaseController{

    private final AccountService accountService;
    private final LocalizationService localizationService;

    public AccountController(AccountService accountService, LocalizationService localizationService) {
        this.accountService = accountService;
        this.localizationService = localizationService;
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse<Account>> saveAccount(@RequestBody AccountDto accountDto
            , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws Exception {
        Account account=accountService.saveAccount(accountDto,language);
        ApiResponse<Account> response = new ApiResponse<>(0, "", account);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/delete/{iban}")
    public ResponseEntity<ApiResponse<String>> deleteAccount(@PathVariable String iban
            , @RequestHeader(name = LANGUAGE_HEADER, defaultValue = LANGUAGE_HEADER_VALUE) String language) throws Exception {
        accountService.deleteAccount(iban,language);
        ApiResponse<String> response = new ApiResponse<>(0, ""
                , localizationService.getMessage("Account.deletion", language));
        return ResponseEntity.ok(response);
    }
}
