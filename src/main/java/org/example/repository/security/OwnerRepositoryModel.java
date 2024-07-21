package org.example.repository.security;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Entity
@Data
@NoArgsConstructor
@Table(name="user")
public class OwnerRepositoryModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String password;

    @Column(name="email", unique = true, length = 255)
    private String email;

    @Column(name="location")
    private String location;

    @Column(name="telephone")
    private String telephone;

    @Column(name="description")
    private String description;

    @Column(name="profil_picture")
    private String profilPicture;

    @Column(name="adresse")
    private String adresse;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;



// Implementing methods for security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}

