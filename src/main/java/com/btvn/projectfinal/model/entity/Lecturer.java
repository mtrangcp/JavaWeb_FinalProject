package com.btvn.projectfinal.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lecturers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public String getFullName() {
        if (user != null && user.getProfile() != null) {
            return user.getProfile().getFullName();
        }
        return user != null ? user.getUsername() : "";
    }
}