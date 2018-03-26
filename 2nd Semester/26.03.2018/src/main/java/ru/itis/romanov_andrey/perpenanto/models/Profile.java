package ru.itis.romanov_andrey.perpenanto.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "person_name")
    private String personName;

    @Column(name = "person_surname")
    private String personSurname;

    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "user", name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "busket", name = "user_id", referencedColumnName = "id")
    private Busket busket;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(table = "address_to_user", name = "address_id", referencedColumnName = "id")
    private List<AddressToUser> addresses;

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        List<Long> addressIDs = this.addresses.stream()
                                                  .map(AddressToUser::getId)
                                                  .collect(Collectors.toList());

        sb.append("Profile{")
          .append("personName = ").append(this.getPersonName())
          .append(", personSurname = ").append(this.getPersonSurname())
          .append(", userId = ").append(this.getUser().getId())
          .append(", addressIDs = ").append(addressIDs)
          .append("}");

        return sb.toString();

    }
}
