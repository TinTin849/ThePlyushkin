package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.util.VisibilityLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "items")
@NoArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id", referencedColumnName = "id")
    private Collection collection;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VisibilityLevel visibility;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToMany(mappedBy = "item")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<ItemFeature> featuresOfItem;

    @OneToMany(mappedBy = "item")
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<ItemImage> itemImages;

    @Override
    public String toString() {
        return "CollectionItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
