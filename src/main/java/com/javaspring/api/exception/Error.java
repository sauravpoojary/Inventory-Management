package com.javaspring.api.exception;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Error implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3557201271009854510L;
    private ErrorCode code;
    private String property;
    private String message;

    public Error() {
        super();
    }

    public Error(ErrorCode code, String property, String message) {
        this.code = code;
        this.message = message;
        this.property = property;
    }

}
