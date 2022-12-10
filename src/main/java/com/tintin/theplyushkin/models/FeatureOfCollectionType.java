package com.tintin.theplyushkin.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "features_of_collection_type")
@NoArgsConstructor
@Getter
@Setter
public class FeatureOfCollectionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_type", referencedColumnName = "id")
    private TypeOfCollection collectionType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_feature", referencedColumnName = "id")
    private Feature itemFeature;
}
