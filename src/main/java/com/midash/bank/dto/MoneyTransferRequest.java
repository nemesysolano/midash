package com.midash.bank.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Jacksonized @Builder(toBuilder=true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@ToString
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE, force = true)
public class MoneyTransferRequest implements Serializable {
    @NotNull
    @NotBlank
    public final String sourceAccountId;

    @NotNull
    @NotBlank
    public final String targetAccountId;

    @Min(0)
    public final double amount;
}
