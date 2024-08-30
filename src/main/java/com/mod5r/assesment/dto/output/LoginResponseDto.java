package com.mod5r.assesment.dto.output;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
public class LoginResponseDto implements Serializable {
    private String token;
    private long expiresIn;
}

