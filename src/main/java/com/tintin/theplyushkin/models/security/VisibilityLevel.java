package com.tintin.theplyushkin.models.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "visibility_levels")
@NoArgsConstructor
@Getter
@Setter
public class VisibilityLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "level_name")
    private String levelName;

    @OneToMany(mappedBy = "visibility")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Person> people;

    public VisibilityLevel(String levelName) {
        this.levelName = levelName;
    }
}
