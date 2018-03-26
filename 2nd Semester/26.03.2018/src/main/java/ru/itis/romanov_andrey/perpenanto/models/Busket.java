package ru.itis.romanov_andrey.perpenanto.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "busket")
public class Busket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(table = "profile", name = "user_id", referencedColumnName = "id")
    private Profile userProfile;

    @Column(name = "product_id")
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(table = "product", name = "product_id", referencedColumnName = "id")
    private List<Product> products;

    @Override
    public String toString() {

        List<Long> productsIDs = this.products.stream()
                                              .map(Product::getId)
                                              .collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        sb.append("Busket{")
          .append("id = ").append(this.getId())
          .append(", userProfileID = ").append(this.userProfile.getId())
          .append(", productsIDs = ").append(productsIDs)
          .append("}");

        return sb.toString();

    }
}
