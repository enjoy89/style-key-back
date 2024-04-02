package com.thekey.stylekeyserver.item.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thekey.stylekeyserver.brand.entity.Brand;
import com.thekey.stylekeyserver.category.entity.Category;
import com.thekey.stylekeyserver.coordinatelook.entity.CoordinateLook;
import com.thekey.stylekeyserver.image.entity.Image;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_title")
    private String title;

    @Column(name = "item_sales_link")
    private String sales_link;

    @OneToOne
    @JoinColumn(name = "item_image_id")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinate_look_id")
    @JsonIgnore
    private CoordinateLook coordinateLook;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "like_count")
    private Integer likeCount;

    @Builder
    public Item(String title, String sales_link, Image image, Brand brand, Category category) {
        this.title = title;
        this.sales_link = sales_link;
        this.image = image;
        this.brand = brand;
        this.category = category;
        this.likeCount = 0;
    }

    public void update(String title, String sales_link, Brand brand, Category category) {
        this.title = title;
        this.sales_link = sales_link;
        this.brand = brand;
        this.category = category;
    }

    public void setImage(Image newImage) {
        if(this.image != null) {
            this.image.setUnused();
        }
        this.image = newImage;
    }

    public void setCoordinateLook(CoordinateLook coordinateLook) {
        this.coordinateLook = coordinateLook;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }
}
