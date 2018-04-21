package com.romanidze.perpenanto.domain.admin;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

/**
 * 07.04.2018
 *
 * @author Andrey Romanov (steampart@gmail.com)
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class AddressToUser {

    private Long userId;
    private String country;
    private Integer postalCode;
    private String city;
    private String street;
    private Integer homeNumber;

}
