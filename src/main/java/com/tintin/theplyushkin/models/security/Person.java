package com.tintin.theplyushkin.models.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "people")
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Никнейм не должен быть пустым")
    @Size(min = 2, max = 100, message = "Никнейм должен быть от 2 до 100 символов")
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "access", referencedColumnName = "id")
    private AccessLevel role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visibility", referencedColumnName = "id")
    private VisibilityLevel visibility;

    public Person(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}