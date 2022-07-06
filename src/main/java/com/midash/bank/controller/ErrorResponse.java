package com.midash.bank.controller;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Jacksonized @Builder(toBuilder=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE, force = true)
/**
 * Baseclass for error responses.
 */
public class ErrorResponse {
    /**
     * <p>Frontend developers can translate this error code into a meaningful error message</p>
     */
    public final int errorCode;

    /**
     * <p>This description shouldn't be displayed in UI as it's intended only for debugging purposes.</p>
     */
    public final String description;

    /**
     * <p>Used to report problem in posted forms</p>
     * <p>Form field names are used as map keys and error are represented by integer or key words.</p>
     */
    public final Map<String, String> formErrors;

    /**
     * Exception class name (short version)
     */
    public final String cause;
}
