package com.project.soohofit.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ResponseStatus {
    SUCCESS("0000", "success"),
    ERROR_REQUIRED_PARAM("9000", "required parameter missing"),
    NO_CONTENT("9001", "empty objects"),
    SYSTEM_ERROR("9999", "system error");

    private String code;
    private String message;

}
