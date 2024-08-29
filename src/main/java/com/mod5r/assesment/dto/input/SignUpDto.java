package com.mod5r.assesment.dto.input;

import lombok.Builder;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class SignUpDto implements Serializable {
    private String email;
    private String password;
    private String fistName;
    private String secondName;

}

