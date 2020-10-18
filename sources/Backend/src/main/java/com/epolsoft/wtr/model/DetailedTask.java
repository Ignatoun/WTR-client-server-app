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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DetailedTask")
public class DetailedTask {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="detailedTaskId")
    private Integer detailedTaskId;

    @Column(name="detailedTaskName")
    private String detailedTaskName;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name="taskId")
    private Tasks task;

    @OneToMany(mappedBy = "detailedTask")
    @JsonIgnore
    private Set<ReportDetails> reportDetails;

    @Override
    public String toString(){
        return "DetailedTask ["
                + "DetailedTaskId=" + detailedTaskId
                + ", DetailedTaskName=" + detailedTaskName
                + ", TaskId=" + task.getTaskId() + "]";
    }
}
