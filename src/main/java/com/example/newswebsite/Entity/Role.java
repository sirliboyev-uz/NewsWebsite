package com.example.newswebsite.Entity;

import com.example.newswebsite.Entity.Enums.RoleTypes;
import com.example.newswebsite.Entity.Template.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role extends AbstractEntity {

    private String roleName;

    @Enumerated(value = EnumType.STRING)
    @ElementCollection
    private List<RoleTypes> roleTypes;

    @Column(columnDefinition = "text")
    private String description;


}
