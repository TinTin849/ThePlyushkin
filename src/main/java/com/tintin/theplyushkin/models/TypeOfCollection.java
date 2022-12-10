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

    @OneToMany(mappedBy = "collectionType")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<FeatureOfCollectionType> featuresOfCollectionType;
}
