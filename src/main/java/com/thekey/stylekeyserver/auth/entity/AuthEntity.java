package com.thekey.stylekeyserver.auth.entity;

import java.time.OffsetDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(name = "created_at")
    private OffsetDateTime createdAt = OffsetDateTime.now();

    @Column(nullable = false)
    private String password;

    public static AuthEntity of(String userId, String password, OffsetDateTime createdAt){
        return AuthEntity.builder()
                .userId(userId)
                .password(password)
                .createdAt(createdAt)
                .build();
     }
  
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
