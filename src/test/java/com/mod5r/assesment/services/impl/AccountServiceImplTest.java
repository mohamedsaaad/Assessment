package com.mod5r.assesment.services.impl;

import com.mod5r.assesment.dto.input.AccountDto;
import com.mod5r.assesment.entities.Account;
import com.mod5r.assesment.entities.User;
import com.mod5r.assesment.exception.BusinessException;
import com.mod5r.assesment.repositories.AccountRepository;
import com.mod5r.assesment.repositories.UserRepository;
import com.mod5r.assesment.services.LocalizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAccount() throws Exception {
        AccountDto accountDto = new AccountDto("iban123", "Test Account");
        Account account = new Account(1L, null, "iban123", "Test Account");

        when(accountRepository.findByIban(anyString())).thenReturn(Optional.empty());
        when(localizationService.getMessage(anyString(), anyString())).thenReturn("Message");
        User user = new User();
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account savedAccount = accountService.saveAccount(accountDto, "en");
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void testDeleteAccountNotFound() {
        when(accountRepository.findByIban(anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> {
            accountService.deleteAccount("iban123", "en");
        });
    }
}

