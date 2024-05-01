package com.example.job3.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity  {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String name;
    private String surname;
    private Short age;
    private String encodedPassword;
    private String username;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;


    @OneToOne
    @JoinColumn(name = "basket_id", referencedColumnName = "uuid")
    @JsonIgnore
    private BasketEntity basket;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProductEntity> products;




    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}

