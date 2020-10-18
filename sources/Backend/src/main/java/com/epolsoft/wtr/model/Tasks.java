package com.epolsoft.wtr.model;

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
@Table(name="Task")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId")
    private Integer taskId;

    @Column(name = "taskName")
    private String taskName;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="featureId")
    private Feature feature;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    private Set<DetailedTask> detailedTasks;

    @Override
    public String toString() {
        return "Task ["
                + "taskId=" + taskId
                + ", taskName=" + taskName
                + ", featureId=" + feature.getFeatureId() + "]";
    }
}

