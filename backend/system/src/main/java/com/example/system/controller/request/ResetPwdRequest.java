package com.example.system.controller.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ResetPwdRequest implements Serializable {

    private String email;

    private String password;

    /**
     * email code.
     */
    private String code;
}
