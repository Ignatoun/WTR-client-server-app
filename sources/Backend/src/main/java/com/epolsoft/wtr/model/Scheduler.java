package com.epolsoft.wtr.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Scheduler")
public class Scheduler {

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Column(name="paramId")
    private Integer paramId;

    @Column(name="paramName")
    private String paramName;

    @Column(name="value")
    private String value;

    @Override
    public String toString() {
        return "Scheduler{" +
                "paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
