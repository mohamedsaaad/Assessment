package com.mod5r.assesment.services;

import com.mod5r.assesment.dto.input.AccountDto;
import com.mod5r.assesment.entities.Account;

public interface AccountService {
    Account saveAccount(AccountDto accountDto, String language) throws Exception;

    void deleteAccount(String iban, String language) throws Exception;
}
