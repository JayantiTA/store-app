package com.example.store_application.controllers.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PayOrderReq {
    @NotNull
    @JsonProperty("payment_method")
    private String paymentMethod;
}
