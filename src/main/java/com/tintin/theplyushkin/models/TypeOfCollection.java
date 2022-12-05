package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.models.security.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "collection_types")
@NoArgsConstructor
@Getter
@Setter
public class TypeOfCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator", referencedColumnName = "id")
    private Person creator;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visibility", referencedColumnName = "id")
    private VisibilityLevel visibility;
}
