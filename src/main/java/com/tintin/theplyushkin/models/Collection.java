package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.security.Person;
import com.tintin.theplyushkin.models.security.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collections")
@NoArgsConstructor
@Getter
@Setter
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<CollectionItem> itemsOfCollection;
}
