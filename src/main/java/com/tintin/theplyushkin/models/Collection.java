package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.models.security.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "collections")
@NoArgsConstructor
@Getter
@Setter
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Person creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_type", referencedColumnName = "id")
    private TypeOfCollection typeOfCollection;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "img")
    @Basic(fetch = FetchType.LAZY)
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visibility", referencedColumnName = "id")
    private VisibilityLevel visibility;
}
