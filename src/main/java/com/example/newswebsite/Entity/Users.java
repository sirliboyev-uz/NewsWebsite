package com.example.newswebsite.Entity;

import com.example.newswebsite.Entity.Enums.RoleTypes;
import com.example.newswebsite.Entity.Template.AbstractEntity;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Users extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    public Users(String fullName, String username, String password, Role role, boolean enabled, String emailCode) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.emailCode=emailCode;
    }

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @ToString.Exclude
    private Role role;

    private boolean enabled;

    private String emailCode;

    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked=true;
    private boolean isCredentialsNonExpired=true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleTypes> roleTypes = role.getRoleTypes();
        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        for (RoleTypes i: roleTypes){
            grantedAuthorities.add(new SimpleGrantedAuthority(i.name()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }
}
