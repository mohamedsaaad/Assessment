package com.mod5r.assesment.dto.input;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@Builder
@Data
public class UserVerificationDto implements Serializable {
    private String email;
    private String verificationCode;
}
