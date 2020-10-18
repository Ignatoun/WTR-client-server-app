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
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Feature")
public class Feature {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="featureId")
    private Integer featureId;

    @Column(name = "featureName")
    private String featureName;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="projectId")
    private Project project;

    @OneToMany(mappedBy = "feature")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    @OneToMany(mappedBy = "feature")
    @JsonIgnore
    private Set<Tasks> task;

    @Override
    public String toString(){
        return "Feature {"
                + "FeatureId = " + featureId
                + ", FeatureName = " + featureName
                + ", ProjectId = " + project.getProjectId()
                + "}";
    }
}
