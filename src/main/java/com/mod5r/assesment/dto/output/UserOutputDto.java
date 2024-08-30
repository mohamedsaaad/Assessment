package com.mod5r.assesment.dto.output;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class UserOutputDto implements Serializable {
    private String userName;
    private String email;
}
