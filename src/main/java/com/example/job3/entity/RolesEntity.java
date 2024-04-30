package com.example.job3.entity;





import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    // Добавлен метод getName()
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String name;

    // Конструкторы, геттеры и сеттеры
    // Другие методы, если необходимо
}
