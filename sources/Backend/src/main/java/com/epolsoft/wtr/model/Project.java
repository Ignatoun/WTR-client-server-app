package com.epolsoft.wtr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Project")
public class Project {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="projectId")
    private Integer projectId;

    @Column(name="projectName")
    private String projectName;

    @Column(name="startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name="endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<Feature> feature;

    @Override
    public String toString() {
        return "Project ["
                + "projectId=" + projectId
                + ", projectName=" + projectName
                + ", startDate=" + startDate
                + ", endDate=" + endDate + "]";
    }
}
