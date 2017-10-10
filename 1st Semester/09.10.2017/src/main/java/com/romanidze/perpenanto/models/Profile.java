package com.romanidze.perpenanto.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Profile {

    private Long id;
    private String country;
    private String gender;
    private String subscription;
    private Long userId;

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("Profile{")
          .append("id = ").append(this.getId())
          .append(", country = ").append(this.getCountry())
          .append(", gender = ").append(this.getGender())
          .append(", subscripttion = ").append(this.getSubscription())
          .append(", userID = ").append(this.getUserId());

        return sb.toString();

    }

}
