package com.coworking.model;

import java.math.BigDecimal;

import com.coworking.enums.SpaceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "spaces",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_space_name", columnNames = "name")
    }
)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Space extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SpaceType type;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Builder.Default
    @Column(nullable = false)
    private Boolean enabled = true;
    
    public boolean isAvailable() {
        return Boolean.TRUE.equals(enabled);
    }

}
