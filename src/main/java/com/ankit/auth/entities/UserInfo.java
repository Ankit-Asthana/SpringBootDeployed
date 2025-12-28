package com.ankit.auth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(                                                 // joining two tables
            name = "users_roles",                               // name of the joined table
            joinColumns = @JoinColumn(name = "user_id"),        // joining this table's column name
            inverseJoinColumns = @JoinColumn(name = "role_id")  // joining other table's column name
    )
    private Set<Role> userRoles = new HashSet<>();

}
