package com.javaspring.api.constants;

public class MessageConstant {

    public static final String MANDATORY_HEADER_MISSING = "Missing Mandatory Header ";

    public static final String HEADER_MISSING = "Missing request header";

    public static final String INVALID_MISSING = " value is empty or invalid or missing";
    
    public static final String INTERNAL_SERVER_ERROR = "Operation failed because of an internal network error";
    
    public static final String MALFORMED_REQUEST = "Malformed Promise Payload";

    public static final String METHOD_NOT_ALLOWED = "Method Not Allowed";

    public static final String MEDIA_TYPE_NOT_SUPPORTED = "Only application/json is supported in request body";
    

    MessageConstant() {
        throw new IllegalStateException("Utility class");
    }

}
