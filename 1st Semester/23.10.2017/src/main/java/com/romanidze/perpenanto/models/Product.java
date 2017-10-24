package com.romanidze.perpenanto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Product {

    private Long id;
    private String status;
    private Timestamp createdAt;

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("Product{")
          .append("id = ").append(this.getId())
          .append(". status = ").append(this.getStatus())
          .append(", created_at = ").append(this.getCreatedAt())
          .append("}");

        return sb.toString();

    }

}
