package com.epolsoft.wtr.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="roleId")
    private Integer roleId;

    @Column(name="roleName")
    private String roleName;

    @JsonIgnore
    @ManyToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinTable(name="UserRole",
            joinColumns=@JoinColumn(name="roleId"),
            inverseJoinColumns = @JoinColumn(name="userId"))
    private Set<User> user;

    public Role (Integer roleId, String roleName)
    {
        this.roleId=roleId;
        this.roleName=roleName;
    }

    @Override
    public String toString() {
        return "Role ["
                + "roleId = " + roleId
                + ", roleName = " + roleName + "]";
    }
}
