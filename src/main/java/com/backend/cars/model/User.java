package com.backend.cars.model;

import com.backend.cars.validators.UniqueEmail;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private int user_id;

    @NotEmpty(message = "Username may not be empty.")
    private String username;

    @Size(min=8, message = "Password is to short. Password must have at least 8 characters.")
    private String password;

    @UniqueEmail
    @Email(message = "Provided email is not correct email address.")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    //set of groups that user belongs to as USER
    @ManyToMany
    private Set<UserGroup> asUserUserGroups;

    @OneToMany
    @JoinTable(
            name="admin_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private Set<UserGroup> asAdminUserGroups;

}
