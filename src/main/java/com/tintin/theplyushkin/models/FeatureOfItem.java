package com.tintin.theplyushkin.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "collection_item_features")
@NoArgsConstructor
@Getter
@Setter
public class FeatureOfItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item", referencedColumnName = "id")
    private CollectionItem collectionItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature", referencedColumnName = "id")
    private Feature itemFeature;

    @Column(name = "data")
    private String data;
}
