package com.tintin.theplyushkin.models;

import com.tintin.theplyushkin.models.util.VisibilityLevel;
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
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_type", referencedColumnName = "id")
    private CollectionType collectionType;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "visibility")
    private VisibilityLevel visibility;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "collection", fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<Item> items;
}
