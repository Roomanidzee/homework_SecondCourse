package com.romanidze.perpenanto.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ProductInfo {

    private Long id;
    private User user;
    private List<Product> products;

}
