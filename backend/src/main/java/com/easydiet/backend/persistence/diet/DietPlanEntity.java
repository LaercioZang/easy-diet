package com.easydiet.backend.persistence.diet;

import com.easydiet.backend.persistence.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "diet_plan")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DietPlanEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int tdee;

    @Lob
    @Column(nullable = false)
    private String weekDistributionJson;

    @Column(nullable = false)
    private Instant createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DietPlanStatus status;

    @Column
    private Instant activatedAt;


}
