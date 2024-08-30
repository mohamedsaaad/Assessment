package com.mod5r.assesment.dto.input;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Setter
@Getter
public class AccountDto {
    private String iban;
    private String accountTitle;
}
