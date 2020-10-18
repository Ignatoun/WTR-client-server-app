package com.epolsoft.wtr.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Department")
public class Department {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="departmentId")
    private Integer departmentId;

    @Column(name="departmentName")
    private String departmentName;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<User> user;

    public Department(Integer departmentId, String departmentName)
    {
        this.departmentId=departmentId;
        this.departmentName=departmentName;
    }

    @Override
    public String toString() {
        return "Department ["
                + "departmentId=" + departmentId
                + ", departmentName=" + departmentName + "]";
    }
}
