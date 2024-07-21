package org.example.repository.owner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="user")
public class OwnerRepositoryModel {
        @Column(name="id")
        private Long id;

        @Column(name="username")
        private String username;

        @Column(name="email")
        private String email;

        @Column(name="location")
        private String location;

        @Column(name="telephone")
        private String telephone;
        @Column(name="description")
        private String description;

        @Column(name="profil_picture")
        private String profil_icture;

        @Column(name="adresse")
        private String adresse;

    }
