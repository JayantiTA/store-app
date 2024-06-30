package com.example.store_application.controllers.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderReq {
    @NotBlank
    private String address;

    @NotNull
    @JsonProperty("item_ids")
    private List<Long> itemIds;
}
