package com.mod5r.assesment.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mod5r.assesment.dto.input.AccountDto;
import com.mod5r.assesment.entities.Account;
import com.mod5r.assesment.services.AccountService;
import com.mod5r.assesment.services.LocalizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private LocalizationService localizationService;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void testSaveAccount() throws Exception {
        AccountDto accountDto =new AccountDto("iban123", "Test Account");
        Account account = new Account(1L, null, "iban123", "Test Account");

        when(accountService.saveAccount(any(AccountDto.class), anyString())).thenReturn(account);

        mockMvc.perform(post("/account/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(accountDto))
                        .header("LANGUAGE_HEADER", "en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.iban").value("iban123"))
                .andExpect(jsonPath("$.data.accountTitle").value("Test Account"));

        verify(accountService, times(1)).saveAccount(any(AccountDto.class), anyString());
    }

    @Test
    public void testDeleteAccount() throws Exception {
        when(localizationService.getMessage(anyString(), anyString())).thenReturn("Account deleted");

        mockMvc.perform(delete("/account/delete/iban123")
                        .header("LANGUAGE_HEADER", "en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Account deleted"));

        verify(accountService, times(1)).deleteAccount(anyString(), anyString());
    }
}
