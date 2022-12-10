package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.security.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "collection_items")
@NoArgsConstructor
@Getter
@Setter
public class CollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection", referencedColumnName = "id")
    private Collection collection;

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

    @OneToMany(mappedBy = "collectionItem")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<FeatureOfItem> featuresOfCollectionItem;
}
