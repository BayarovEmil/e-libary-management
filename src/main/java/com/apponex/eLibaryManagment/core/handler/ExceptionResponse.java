package com.apponex.eLibaryManagment.core.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.HashMap;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionResponse {
    private int businessErrorCodes;
    private String businessErrorDescription;
    private String error;
    private Set<String> validationErrors;
    private HashMap<String,String> errors;
}
