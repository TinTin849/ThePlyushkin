package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.util.VisibilityLevel;
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
public class CollectionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VisibilityLevel visibility;

    @OneToMany(mappedBy = "collectionType")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Feature> featuresOfCollectionType;
}
