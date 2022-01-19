package com.pvt73.recycling.model.dao;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class LitteredPlace {

    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String userId;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    private CleaningStatus cleaningStatus;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime cleanedAt;
    private String cleanedBy;

    @Transient
    private double distance;
    private String address;
    @NotNull
    private LatLng coordinates;


    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Image> litteredImageSet = new HashSet<>();
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Image> cleanedImageSet = new HashSet<>();

    private boolean event;
    private String description;


    public LitteredPlace(LatLng coordinates, String userId) {
        this.coordinates = coordinates;
        this.userId = userId;

    }

    public boolean containImage(String imageId) {

        for (Image image : litteredImageSet) {
            if (image.getId().equals(imageId))
                return true;
        }

        for (Image image : cleanedImageSet) {
            if (image.getId().equals(imageId)) {
                return true;
            }
        }

        return false;
    }


    public void setCleaningStatus(CleaningStatus status) {
        if (status == CleaningStatus.CLEAN)
            cleanedAt = LocalDateTime.now();

        this.cleaningStatus = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LitteredPlace)) return false;

        LitteredPlace that = (LitteredPlace) o;

        return getCoordinates().equals(that.getCoordinates());
    }

    @Override
    public int hashCode() {
        return getCoordinates().hashCode();
    }
}
