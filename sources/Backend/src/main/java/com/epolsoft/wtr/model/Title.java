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
@Table(name = "Title")
public class Title {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="titleId")
    private Integer titleId;

    @Column(name="titleName")
    private String titleName;

    @OneToMany(mappedBy = "title")
    @JsonIgnore
    private Set<User> user;

    public Title(Integer titleId, String titleName)
    {
        this.titleId=titleId;
        this.titleName=titleName;
    }

    @Override
    public String toString(){
        return "Title ["
                + "titleId=" + titleId
                + ", titleName=" + titleName + "]";
    }
}