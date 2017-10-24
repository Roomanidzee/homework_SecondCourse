package com.romanidze.perpenanto.models.temp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class TempProfileInfo {

    private Long id;
    private Long userId;
    private String country;
    private Integer postIndex;
    private String city;
    private String street;
    private Integer homeNumber;

}
