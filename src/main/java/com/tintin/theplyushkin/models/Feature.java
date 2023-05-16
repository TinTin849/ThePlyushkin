package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.util.DataType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "features")
@NoArgsConstructor
@Getter
@Setter
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_type_id", referencedColumnName = "id")
    private CollectionType collectionType;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "data_type")
    private DataType dataType;
}
