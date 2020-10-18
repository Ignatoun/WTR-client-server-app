package com.epolsoft.wtr.model;

import com.epolsoft.wtr.model.Enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ReportDetails")
public class ReportDetails {

    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column(name = "reportDetailsId")
    private Integer reportDetailsId;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date reportDetailsDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="factorId")
    private Factor factor;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="locationId")
    private Location location;

    @Column(name = "hours")
    private Double hours;

    @Column(name = "workUnits")
    private Double workUnits;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="detailedTaskId")
    private DetailedTask detailedTask;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="taskId")
    private Tasks task;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="featureId")
    private Feature feature;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="projectId")
    private Project project;

    @Column(name = "comments")
    private String comments;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="Report",
            joinColumns=@JoinColumn(name="reportDetailsId"),
            inverseJoinColumns = @JoinColumn(name="userId"))
    private Set<User> users;

    public ReportDetails( Date reportDetailsDate, Status status,
                          Factor factor, Location location, Double hours, Double workUnits,
                          DetailedTask detailedTask, Tasks task, Feature feature, Project project, String comments) {
        this.reportDetailsDate = reportDetailsDate;
        this.status = status;
        this.factor = factor;
        this.location = location;
        this.hours = hours;
        this.workUnits = workUnits;
        this.detailedTask = detailedTask;
        this.task = task;
        this.feature = feature;
        this.project = project;
        this.comments = comments;
    }

}
