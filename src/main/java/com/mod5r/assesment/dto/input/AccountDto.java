package com.mod5r.assesment.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private String iban;
    private String accountTitle;
}
