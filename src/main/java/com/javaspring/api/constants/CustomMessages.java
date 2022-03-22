package com.javaspring.api.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomMessages {

    EXCEPTION_MESSAGE("Caught exception"),
    METHOD("method");
   

    private final String value;

}