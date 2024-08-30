package com.mod5r.assesment.services.impl;

import com.mod5r.assesment.dto.input.AccountDto;
import com.mod5r.assesment.entities.Account;
import com.mod5r.assesment.entities.User;
import com.mod5r.assesment.exception.BusinessException;
import com.mod5r.assesment.repositories.AccountRepository;
import com.mod5r.assesment.services.LocalizationService;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl {
    AccountRepository accountRepository;
    private final LocalizationService localizationService;

    public AccountServiceImpl(AccountRepository accountRepository, LocalizationService localizationService) {
        this.accountRepository = accountRepository;
        this.localizationService = localizationService;
    }

    public Account saveAccount(AccountDto accountDto,String language) throws Exception {
        if(accountRepository.findByIban(accountDto.getIban()).isPresent()){
            throw new BusinessException(localizationService.getMessage("duplicate.iban",language));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Account account=Account
                .builder()
                .user(currentUser)
                .iban(accountDto.getIban())
                .accountTitle(accountDto.getAccountTitle())
                .build();
        return accountRepository.save(account);
    }

    public void deleteAccount(String iban,String language) throws Exception{
        if(accountRepository.findByIban(iban).isPresent()){
            throw new BusinessException(localizationService.getMessage("duplicate.iban",language));
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if(null !=currentUser.getBankAccounts() && currentUser.getBankAccounts().isEmpty()){
            Optional<Account> deletedAccount=currentUser
                    .getBankAccounts()
                    .stream()
                    .filter(acc->iban.equals(acc.getIban()))
                    .findFirst();
            if(deletedAccount.isEmpty()){
                throw new BusinessException(localizationService.getMessage("not.your.account",language));
            }
            accountRepository.delete(deletedAccount.get());
        }

    }
}
