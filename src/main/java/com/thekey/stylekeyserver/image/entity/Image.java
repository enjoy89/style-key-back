package com.thekey.stylekeyserver.image.entity;

import com.thekey.stylekeyserver.brand.entity.Brand;
import com.thekey.stylekeyserver.coordinatelook.entity.CoordinateLook;
import com.thekey.stylekeyserver.item.entity.Item;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "image_type", nullable = false)
    private Type type;

    @Column(name = "image_file_name")
    private String fileName;

    @Column(name = "image_is_used")
    private Boolean isUsed;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "image")
    private Brand brand;

    @OneToOne(mappedBy = "image")
    private Item item;

    @OneToOne(mappedBy = "image")
    private CoordinateLook coordinateLook;

    @Builder
    public Image(String url, Type type, String fileName, Boolean isUsed) {
        this.url = url;
        this.type = type;
        this.fileName = fileName;
        this.isUsed = isUsed;
    }

    public void setUnused() {
        this.isUsed = false;
        this.deletedAt = LocalDateTime.now().plusDays(1);
    }
}