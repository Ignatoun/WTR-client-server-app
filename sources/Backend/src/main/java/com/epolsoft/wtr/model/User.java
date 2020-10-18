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
import javax.persistence.ManyToOne;
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
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer userId;

    @Column(name = "userName")
    private String userName;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="titleId")
    private Title title;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="departmentId")
    private Department department;

    @ManyToMany(mappedBy = "user",fetch= FetchType.EAGER)
    private Set<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "users",fetch= FetchType.LAZY)
    private Set<ReportDetails> reportDetails;

    public User(Integer userId, String userName, String firstName, String lastName, String email, Title title, Department department) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.department = department;
    }

    @Override
    public String toString() {
        return "User{"
                + "userId=" + userId
                + ", userName=" + userName
                + ", firstName=" + firstName
                + ", lastName=" + lastName
                + ", email=" + email
                + ", title=" + title
                + ", department=" + department
                + '}';
    }
}
