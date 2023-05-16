package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.util.AccessLevel;
import com.tintin.theplyushkin.models.util.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "access")
    private AccessLevel role;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VisibilityLevel visibility;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "img_url")
    private String imgUrl;

    public User(String username, String email, String phone) {
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