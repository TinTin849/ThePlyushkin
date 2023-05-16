package com.tintin.theplyushkin.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "item_features")
@NoArgsConstructor
@Getter
@Setter
public class ItemFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "feature_id", referencedColumnName = "id")
    private Feature feature;

    @Column(name = "data")
    private String data;

    public ItemFeature(Item item, Feature feature, String data) {
        this.item = item;
        this.feature = feature;
        this.data = data;
    }
}
