package com.romanidze.localetask.forms;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.ToString;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 23.03.2018
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
public class TripRequestForm implements Serializable {

    private static final long serialVersionUID = 1435274235;

    @NotNull
    @NotEmpty
    private String country;

    @NotNull
    @NotEmpty
    private String departureDate;

    @NotNull
    @NotEmpty
    private String arrivalDate;

}
