package com.javaspring.api.exception;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class ErrorResponse implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3775109176295497115L;
    private List<Error> errors = new ArrayList<>();

}
